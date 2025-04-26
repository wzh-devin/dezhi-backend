package com.devin.dezhi.service.v1.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.devin.dezhi.common.utils.RedisUtil;
import com.devin.dezhi.constant.RedisKey;
import com.devin.dezhi.dao.v1.user.UserDao;
import com.devin.dezhi.domain.v1.entity.user.User;
import com.devin.dezhi.domain.v1.vo.req.UserInfoReq;
import com.devin.dezhi.domain.v1.vo.resp.LoginResp;
import com.devin.dezhi.service.generate.common.RespEntityGenerate;
import com.devin.dezhi.service.v1.UserService;
import com.devin.dezhi.utils.AssertUtil;
import com.devin.dezhi.utils.PasswordEncrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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

    private final RedisUtil redisUtil;

    private final UserDao userDao;

    private final PasswordEncrypt passwordEncrypt;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public LoginResp loginAccount(final UserInfoReq userInfoReq) {
        userInfoReq.setPassword(passwordEncrypt.encrypt(userInfoReq.getPassword()));
        // 查询数据库判断用户信息是否存在
        User user = userDao.getByReq(userInfoReq);

        AssertUtil.isNotEmpty(user, "用户名，密码错误，请重新登录！！！");

        // 判断用户是否登录，如果未登录则进行登录操作
        if (!StpUtil.isLogin(user.getId())) {
            // 登录
            StpUtil.login(user.getId());

            // 将用户信息存储到Redis中，设置过期时间为30天
            redisUtil.setEx(RedisKey.generateRedisKey(RedisKey.LOGIN_INFO, user.getId()), JSONUtil.toJsonStr(user), 30L, TimeUnit.DAYS);
        }

        return RespEntityGenerate.loginResp(StpUtil.getTokenValue());
    }

    @Override
    public void logout(final Long uid) {
        // 判断用户是否登录
        if (!StpUtil.isLogin(uid)) {
            return;
        }
        // sa-token登出
        StpUtil.logout(uid);

        // 删除登陆的缓存信息
        redisUtil.delete(RedisKey.generateRedisKey(RedisKey.LOGIN_INFO, uid));
    }
}
