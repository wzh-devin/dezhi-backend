package com.devin.dezhi.service.v1;

import com.devin.dezhi.domain.v1.dto.UserInfoDTO;
import com.devin.dezhi.domain.v1.vo.user.LoginVO;
import com.devin.dezhi.domain.v1.vo.user.UserInfoVO;

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
     * @param userInfoDTO 用户信息
     * @return LoginResp
     */
    LoginVO loginAccount(UserInfoDTO userInfoDTO);

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
     * @param userInfoDTO 用户信息
     * @return LoginResp
     */
    LoginVO loginEmail(UserInfoDTO userInfoDTO);

    /**
     * 用户注册.
     * @param userInfoDTO 用户信息
     */
    void signup(UserInfoDTO userInfoDTO);

    /**
     * 忘记密码.
     * @param userInfoDTO 用户信息
     */
    void forgetPassword(UserInfoDTO userInfoDTO);

    /**
     * 获取登录用户信息.
     * @return UserInfoResp
     */
    UserInfoVO getLoginUserInfo();
}
