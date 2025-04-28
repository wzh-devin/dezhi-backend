package com.devin.dezhi.service.v1;

import com.devin.dezhi.domain.v1.vo.req.UserInfoReq;
import com.devin.dezhi.domain.v1.vo.resp.LoginResp;

/**
 * 2025/4/25 18:28.
 *
 * <p></p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
public interface UserService {

    /**
     * 账号登录.
     * @param userInfoReq 用户信息
     * @return LoginResp
     */
    LoginResp loginAccount(UserInfoReq userInfoReq);

    /**
     * 登出.
     * @param uid uid
     */
    void logout(Long uid);

    /**
     * 注销.
     * @param uid uid
     */
    void deregisterAccount(Long uid);

    /**
     * 获取邮箱验证码.
     * @param email 邮箱
     */
    void getEmailCode(String email);

    /**
     * 邮箱验证码登录.
     * @param userInfoReq 用户信息
     * @return LoginResp
     */
    LoginResp loginEmail(UserInfoReq userInfoReq);

    /**
     * 用户注册.
     * @param userInfoReq 用户信息
     */
    void signup(UserInfoReq userInfoReq);
}
