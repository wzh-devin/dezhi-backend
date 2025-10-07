package com.devin.dezhi.service;

import com.devin.dezhi.domain.vo.user.LoginVO;
import com.devin.dezhi.domain.vo.user.UserInfoVO;
import com.devin.dezhi.domain.vo.user.UserInfoQueryVO;

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
     * @param userInfoQueryVO 用户信息
     * @return LoginResp
     */
    LoginVO loginAccount(UserInfoQueryVO userInfoQueryVO);

    /**
     * 登出.
     */
    void logout();

    /**
     * 注销.
     */
    void deregisterAccount();

    /**
     * 获取邮箱验证码.
     * @param email 邮箱
     */
    void getEmailCode(String email);

    /**
     * 邮箱验证码登录.
     * @param userInfoQueryVO 用户信息
     * @return LoginResp
     */
    LoginVO loginEmail(UserInfoQueryVO userInfoQueryVO);

    /**
     * 用户注册.
     * @param userInfoQueryVO 用户信息
     */
    void signup(UserInfoQueryVO userInfoQueryVO);

    /**
     * 忘记密码.
     * @param userInfoQueryVO 用户信息
     */
    void forgetPassword(UserInfoQueryVO userInfoQueryVO);

    /**
     * 获取登录用户信息.
     * @return UserInfoResp
     */
    UserInfoVO getLoginUserInfo();
}
