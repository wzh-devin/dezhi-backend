package com.devin.dezhi.service.v1.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.devin.dezhi.common.utils.RedisUtil;
import com.devin.dezhi.constant.CacheKey;
import com.devin.dezhi.constant.RedisKey;
import com.devin.dezhi.dao.v1.user.PermissionDao;
import com.devin.dezhi.dao.v1.user.RoleDao;
import com.devin.dezhi.dao.v1.user.UserDao;
import com.devin.dezhi.dao.v1.user.UserRoleDao;
import com.devin.dezhi.domain.v1.dto.UserInfoDTO;
import com.devin.dezhi.domain.v1.entity.user.Role;
import com.devin.dezhi.domain.v1.entity.user.User;
import com.devin.dezhi.domain.v1.entity.user.UserRole;
import com.devin.dezhi.domain.v1.vo.user.LoginVO;
import com.devin.dezhi.domain.v1.vo.user.PermissionVO;
import com.devin.dezhi.domain.v1.vo.user.RoleVO;
import com.devin.dezhi.domain.v1.vo.user.UserInfoVO;
import com.devin.dezhi.enums.rbac.RoleEnum;
import com.devin.dezhi.exception.BusinessException;
import com.devin.dezhi.service.extension.mail.MailService;
import com.devin.dezhi.service.extension.sa.StpInterfaceImpl;
import com.devin.dezhi.service.generate.common.RespEntityGenerate;
import com.devin.dezhi.service.generate.user.UserEntityGenerate;
import com.devin.dezhi.service.v1.UserService;
import com.devin.dezhi.utils.AssertUtil;
import com.devin.dezhi.utils.PasswordEncrypt;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 2025/4/25 18:29.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Cache<String, Integer> EMAIL_CODE = Caffeine.newBuilder()
            // 设置初始容量
            .initialCapacity(5)
            // 设置最大容量
            .maximumSize(100)
            // 设置code的过期时间
            .expireAfterWrite(61, TimeUnit.SECONDS)
            .build();

    private final RedisUtil redisUtil;

    private final UserDao userDao;

    private final PasswordEncrypt passwordEncrypt;

    private final UserRoleDao userRoleDao;

    private final MailService mailService;

    private final UserEntityGenerate userEntityGenerate;

    private final RoleDao roleDao;

    private final PermissionDao permissionDao;

    private final StpInterfaceImpl stpInterface;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public LoginVO loginAccount(final UserInfoDTO userInfoDTO) {
        userInfoDTO.setPassword(passwordEncrypt.encrypt(userInfoDTO.getPassword()));
        // 查询数据库判断用户信息是否存在
        User user = userDao.getByDTO(userInfoDTO);

        AssertUtil.isNotEmpty(user, "用户名，密码错误，请重新登录！！！");

        // 判断用户是否登录，如果未登录则进行登录操作
        checkLogin(user);

        return RespEntityGenerate.loginResp(StpUtil.getTokenValueByLoginId(user.getId()));
    }

    @Override
    public void logout(final Long uid) {
        // sa-token登出
        StpUtil.logout(uid);

        // 删除登陆的缓存信息
        redisUtil.delete(RedisKey.generateRedisKey(RedisKey.LOGIN_INFO, uid));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deregisterAccount(final Long uid) {
        // 用户信息登出
        logout(uid);

        // 删除用户角色关联信息
        boolean userRoleRemove = userRoleDao.removeByUserId(uid);
        AssertUtil.isTrue(userRoleRemove, "用户角色关联信息删除失败！！！");

        // 逻辑删除用户信息
        boolean userRemove = userDao.logicRemoveById(uid);
        AssertUtil.isTrue(userRemove, "用户信息删除失败！！！");
    }

    @Override
    public void getEmailCode(final String email) {
        // 生成6位验证码
        Integer code = generateCode();

        // 缓存验证码数据
        EMAIL_CODE.put(generateCodeKey(email), code);

        // 发送验证码
        mailService.sendEmailCode(email, code);
    }

    @Override
    public LoginVO loginEmail(final UserInfoDTO userInfoDTO) {
        // 查询用户信息
        User user = userDao.getByDTO(userInfoDTO);

        AssertUtil.isNotEmpty(user, "抱歉，该邮箱尚未注册，请先进行注册！！！");

        // 校验验证码是否正确
        checkCode(userInfoDTO.getEmail(), userInfoDTO.getCode());

        // 判断用户是否登录，如果未登录则进行登录操作
        checkLogin(user);

        return RespEntityGenerate.loginResp(StpUtil.getTokenValueByLoginId(user.getId()));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void signup(final UserInfoDTO userInfoDTO) {
        // 根据邮箱查询用户
        User user = userDao.getByEmail(userInfoDTO.getEmail());
        AssertUtil.isEmpty(user, "抱歉，该邮箱已被注册，请重新输入！！！");

        // 校验验证码是否正确
        checkCode(userInfoDTO.getEmail(), userInfoDTO.getCode());

        // 将信息保存到数据库
        user = userEntityGenerate.generateRegisterUser(userInfoDTO);
        boolean saveUserResult = userDao.save(user);
        AssertUtil.isTrue(saveUserResult, "抱歉，由于系统异常用户信息注册失败，请联系管理员！！！");

        // 角色管理员赋权
        Role role = roleDao.getRoleByName(RoleEnum.ADMIN.getRole());
        UserRole userRole = userEntityGenerate.generateUserRole(user.getId(), role.getId());
        boolean saveUserRoleResult = userRoleDao.save(userRole);
        AssertUtil.isTrue(saveUserRoleResult, "抱歉，由于系统异常用户权限初始化失败，请联系管理员！！！");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void forgetPassword(final UserInfoDTO userInfoDTO) {
        // 根据邮箱查询用户
        User user = userDao.getByEmail(userInfoDTO.getEmail());
        AssertUtil.isNotEmpty(user, "抱歉，该邮箱尚未注册，请先进行注册！！！");

        // 校验验证码是否正确
        checkCode(userInfoDTO.getEmail(), userInfoDTO.getCode());

        // 登出此用户
        logout(user.getId());

        // 如果新密码和旧密码一致，则直接返回即可
        if (passwordEncrypt.checkPassword(userInfoDTO.getPassword(), user.getPassword())) {
            return;
        }

        // 更新用户密码
        boolean updateResult = userDao.updateById(userEntityGenerate.generateUpdateUser(userInfoDTO, user.getId()));
        AssertUtil.isTrue(updateResult, "抱歉，由于系统异常用户密码更新失败，请联系管理员！！！");
    }

    @Override
    public UserInfoVO getLoginUserInfo() {
        // 获取当前登录用户id
        Long uid = Long.valueOf(StpUtil.getLoginId().toString());

        // 获取登录用户信息
        User user = JSONUtil.toBean(redisUtil.get(RedisKey.generateRedisKey(RedisKey.LOGIN_INFO, uid)), User.class);

        // 如果缓存未命中，则查询数据库
        if (Objects.isNull(user)) {
            user = userDao.getById(uid);
        }

        // 查询所需信息
        List<Long> roleIds = stpInterface.getRoleIds(uid);
        List<Long> permissionIds = stpInterface.getPermissionIds(roleIds);
        List<RoleVO> roleResps = roleDao.listByIds(roleIds)
                .stream()
                .map(role -> {
                    RoleVO roleVO = new RoleVO();
                    roleVO.setName(role.getRole());
                    roleVO.setRemark(role.getRemark());
                    return roleVO;
                }).toList();
        List<PermissionVO> permissionResps = permissionDao.listByIds(permissionIds)
                .stream()
                .map(permission -> {
                    PermissionVO permissionVO = new PermissionVO();
                    permissionVO.setName(permission.getPermission());
                    permissionVO.setRemark(permission.getRemark());
                    return permissionVO;
                }).toList();
        String createUser = userDao.getUsernameById(user.getCreateUserId());
        String updateUser = userDao.getUsernameById(user.getCreateUserId());

        return RespEntityGenerate.generateUserInfoResp(user, roleResps, permissionResps, createUser, updateUser);
    }

    /**
     * 进行登录操作.
     *
     * @param user 用户信息
     */
    private void login(final User user) {
        // 登录操作
        StpUtil.login(user.getId());
    }

    /**
     * 登录续签.
     *
     * @param user 用户信息
     */
    private void loginRefresh(final User user) {
        // 判断Token是否以临期（如果过期，则抛出异常）
        StpUtil.checkActiveTimeout();

        // Token续签
        StpUtil.updateLastActiveToNow();
    }

    /**
     * 检查用户是否登录.
     *
     * @param user 用户信息
     */
    private void checkLogin(final User user) {
        // 判断该用户是否登录
        if (!StpUtil.isLogin(user.getId())) {
            // 进行登录操作
            login(user);
        } else {
            // 登录续签
            loginRefresh(user);
        }

        // 将用户信息存储到Redis中，设置过期时间为7天
        redisUtil.setEx(RedisKey.generateRedisKey(RedisKey.LOGIN_INFO, user.getId()), JSONUtil.toJsonStr(user), 7L, TimeUnit.DAYS);
    }

    /**
     * 生成随机的六位验证码.
     *
     * @return 验证码
     */
    private Integer generateCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return Integer.parseInt(String.format("%06d", code));
    }

    /**
     * 校验验证码是否正确.
     *
     * @param email 邮箱
     * @param code  验证码
     */
    private void checkCode(final String email, final Integer code) {
        boolean check = Optional.ofNullable(EMAIL_CODE.getIfPresent(generateCodeKey(email)))
                .orElseThrow(() -> new BusinessException("验证码已过期，请重新获取！！！"))
                .equals(code);

        AssertUtil.isTrue(check, "验证码错误，请重新输入！！！");

        // 校验通过，则删除此缓存
        EMAIL_CODE.invalidate(generateCodeKey(email));
    }

    /**
     * 生成验证码的缓存Key.
     *
     * @param email 邮箱
     * @return Key
     */
    private String generateCodeKey(final String email) {
        return CacheKey.generateKey(CacheKey.EMAIL_CODE_KEY, email);
    }
}
