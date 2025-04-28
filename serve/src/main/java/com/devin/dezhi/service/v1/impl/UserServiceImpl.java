package com.devin.dezhi.service.v1.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.devin.dezhi.common.utils.RedisUtil;
import com.devin.dezhi.constant.CacheKey;
import com.devin.dezhi.constant.RedisKey;
import com.devin.dezhi.dao.v1.user.RoleDao;
import com.devin.dezhi.dao.v1.user.UserDao;
import com.devin.dezhi.dao.v1.user.UserRoleDao;
import com.devin.dezhi.domain.v1.entity.user.Role;
import com.devin.dezhi.domain.v1.entity.user.User;
import com.devin.dezhi.domain.v1.entity.user.UserRole;
import com.devin.dezhi.domain.v1.vo.req.UserInfoReq;
import com.devin.dezhi.domain.v1.vo.resp.LoginResp;
import com.devin.dezhi.enums.rpac.RoleEnum;
import com.devin.dezhi.exception.BusinessException;
import com.devin.dezhi.service.extension.mail.MailService;
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
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 2025/4/25 18:29.
 *
 * <p></p>
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

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public LoginResp loginAccount(final UserInfoReq userInfoReq) {
        userInfoReq.setPassword(passwordEncrypt.encrypt(userInfoReq.getPassword()));
        // 查询数据库判断用户信息是否存在
        User user = userDao.getByReq(userInfoReq);

        AssertUtil.isNotEmpty(user, "用户名，密码错误，请重新登录！！！");

        // 判断用户是否登录，如果未登录则进行登录操作
        if (!StpUtil.isLogin(user.getId())) {
            login(user);
        }

        return RespEntityGenerate.loginResp(StpUtil.getTokenValue());
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
    public LoginResp loginEmail(final UserInfoReq userInfoReq) {
        // 查询用户信息
        User user = userDao.getByReq(userInfoReq);

        AssertUtil.isNotEmpty(user, "抱歉，该邮箱尚未注册，请先进行注册！！！");

        // 判断该用户是否登录
        if (!StpUtil.isLogin(user.getId())) {
            // 校验验证码是否正确
            checkCode(userInfoReq.getEmail(), userInfoReq.getCode());
            // 进行登录操作
            login(user);
        }

        return RespEntityGenerate.loginResp(StpUtil.getTokenValue());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void signup(final UserInfoReq userInfoReq) {
        // 根据邮箱查询用户
        User user = userDao.getByEmail(userInfoReq.getEmail());

        AssertUtil.isEmpty(user, "抱歉，该邮箱已被注册，请重新输入！！！");

        // 校验验证码是否正确
        checkCode(userInfoReq.getEmail(), userInfoReq.getCode());

        // 将信息保存到数据库
        user = userEntityGenerate.generateRegisterUser(userInfoReq);
        boolean saveUserResult = userDao.save(user);
        AssertUtil.isTrue(saveUserResult, "抱歉，由于系统异常用户信息注册失败，请联系管理员！！！");

        // 角色管理员赋权
        Role role = roleDao.getRoleByName(RoleEnum.ADMIN.getRole());
        UserRole userRole = userEntityGenerate.generateUserRole(user.getId(), role.getId());
        boolean saveUserRoleResult = userRoleDao.save(userRole);
        AssertUtil.isTrue(saveUserRoleResult, "抱歉，由于系统异常用户权限初始化失败，请联系管理员！！！");
    }

    /**
     * 进行登录操作.
     * @param user 用户信息
     */
    private void login(final User user) {
        // 登录操作
        StpUtil.login(user.getId());

        // 将用户信息存储到Redis中，设置过期时间为7天
        redisUtil.setEx(RedisKey.generateRedisKey(RedisKey.LOGIN_INFO, user.getId()), JSONUtil.toJsonStr(user), 7L, TimeUnit.DAYS);
    }

    /**
     * 生成随机的六位验证码.
     * @return 验证码
     */
    private Integer generateCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return Integer.parseInt(String.format("%06d", code));
    }

    /**
     * 校验验证码是否正确.
     * @param email 邮箱
     * @param code 验证码
     */
    private void checkCode(final String email, final Integer code) {
        boolean check = Optional.ofNullable(EMAIL_CODE.getIfPresent(generateCodeKey(email)))
                .orElseThrow(() -> new BusinessException("验证码已过期，请重新获取！！！"))
                .equals(code);

        AssertUtil.isTrue(check, "验证码错误，请重新输入！！！");
    }

    /**
     * 生成验证码的缓存Key.
     * @param email 邮箱
     * @return Key
     */
    private String generateCodeKey(final String email) {
        return CacheKey.generateKey(CacheKey.EMAIL_CODE_KEY, email);
    }
}
