package com.devin.dezhi.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.devin.dezhi.utils.RedisUtil;
import com.devin.dezhi.constant.CacheKey;
import com.devin.dezhi.constant.ErrMsgConstant;
import com.devin.dezhi.constant.RedisKey;
import com.devin.dezhi.dao.user.PermissionDao;
import com.devin.dezhi.dao.user.RoleDao;
import com.devin.dezhi.dao.user.UserDao;
import com.devin.dezhi.dao.user.UserRoleDao;
import com.devin.dezhi.domain.entity.user.Role;
import com.devin.dezhi.domain.entity.user.User;
import com.devin.dezhi.domain.entity.user.UserRole;
import com.devin.dezhi.domain.vo.user.LoginVO;
import com.devin.dezhi.domain.vo.user.PermissionVO;
import com.devin.dezhi.domain.vo.user.RoleVO;
import com.devin.dezhi.domain.vo.user.UserInfoQueryVO;
import com.devin.dezhi.domain.vo.user.UserInfoVO;
import com.devin.dezhi.enums.HttpErrorEnum;
import com.devin.dezhi.enums.rbac.RoleEnum;
import com.devin.dezhi.exception.BusinessException;
import com.devin.dezhi.service.extension.mail.MailService;
import com.devin.dezhi.service.extension.sa.StpInterfaceImpl;
import com.devin.dezhi.service.generate.common.RespEntityGenerate;
import com.devin.dezhi.service.generate.user.UserEntityGenerate;
import com.devin.dezhi.service.UserService;
import com.devin.dezhi.utils.AssertUtil;
import com.devin.dezhi.utils.BeanCopyUtils;
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
    public LoginVO loginAccount(final UserInfoQueryVO userInfoQueryVO) {

        UserInfoQueryVO userInfo = BeanCopyUtils.copy(userInfoQueryVO, UserInfoQueryVO.class);
        userInfo.setPassword(passwordEncrypt.encrypt(userInfoQueryVO.getPassword()));
        // 查询数据库判断用户信息是否存在
        User user = userDao.getByVO(userInfo);

        AssertUtil.isNotEmpty(user, ErrMsgConstant.USER_PASSWORD_ERROR);

        // 判断用户是否登录，如果未登录则进行登录操作
        checkLogin(user);

        return RespEntityGenerate.loginResp(StpUtil.getTokenValueByLoginId(user.getId()));
    }

    @Override
    public void logout() {
        String uid = StpUtil.getLoginId().toString();

        // sa-token登出
        StpUtil.logout(uid);

        // 删除登陆的缓存信息
        redisUtil.delete(RedisKey.generateRedisKey(RedisKey.LOGIN_INFO, uid));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deregisterAccount() {
        // 获取当前用户登录id
        Long uid = Long.valueOf(StpUtil.getLoginId().toString());

        // 用户信息登出
        logout();

        // 删除用户角色关联信息
        userRoleDao.removeByUserId(uid);

        // 删除用户信息
        userDao.removeById(uid);
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
    public LoginVO loginEmail(final UserInfoQueryVO userInfoQueryVO) {
        // 查询用户信息
        User user = userDao.getByVO(userInfoQueryVO);

        AssertUtil.isNotEmpty(user, ErrMsgConstant.EMAIL_NOT_REGISTER);

        // 校验验证码是否正确
        checkCode(userInfoQueryVO.getEmail(), userInfoQueryVO.getCode());

        // 判断用户是否登录，如果未登录则进行登录操作
        checkLogin(user);

        return RespEntityGenerate.loginResp(StpUtil.getTokenValueByLoginId(user.getId()));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void signup(final UserInfoQueryVO userInfoQueryVO) {
        // 根据邮箱查询用户
        User user = userDao.getByEmail(userInfoQueryVO.getEmail());
        AssertUtil.isEmpty(user, ErrMsgConstant.USER_EMAIL_EXIST);

        // 校验验证码是否正确
        checkCode(userInfoQueryVO.getEmail(), userInfoQueryVO.getCode());

        // 将信息保存到数据库
        user = userEntityGenerate.generateRegisterUser(userInfoQueryVO);
        userDao.save(user);

        // 角色管理员赋权
        Role role = roleDao.getRoleByName(RoleEnum.ADMIN.getRole());
        UserRole userRole = userEntityGenerate.generateUserRole(user.getId(), role.getId());
        userRoleDao.save(userRole);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void forgetPassword(final UserInfoQueryVO userInfoQueryVO) {
        // 根据邮箱查询用户
        User user = userDao.getByEmail(userInfoQueryVO.getEmail());
        AssertUtil.isNotEmpty(user, ErrMsgConstant.EMAIL_NOT_REGISTER);

        // 校验验证码是否正确
        checkCode(userInfoQueryVO.getEmail(), userInfoQueryVO.getCode());

        // 登出此用户
        logout();

        // 如果新密码和旧密码一致，则直接返回即可
        if (passwordEncrypt.checkPassword(userInfoQueryVO.getPassword(), user.getPassword())) {
            return;
        }

        // 更新用户密码
        userDao.updateById(userEntityGenerate.generateUpdateUser(userInfoQueryVO, user.getId()));
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

        return RespEntityGenerate.generateUserInfoResp(user, roleResps, permissionResps);
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
     */
    private void loginRefresh() {
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
            loginRefresh();
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
                .orElseThrow(() -> new BusinessException(HttpErrorEnum.EMAIL_CODE_INVALID))
                .equals(code);

        AssertUtil.isTrue(check, ErrMsgConstant.EMAIL_CODE_ERROR);

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
