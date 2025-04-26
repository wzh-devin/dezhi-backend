package com.devin.dezhi.service.generate.common;

import com.devin.dezhi.domain.v1.vo.resp.LoginResp;

/**
 * 2025/4/26 19:21.
 *
 * <p>
 *     通用响应实体生成器
 * </p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
public class RespEntityGenerate {

    /**
     * 登录响应实体生成器.
     * @param token token
     * @return LoginResp
     */
    public static LoginResp loginResp(final String token) {
        LoginResp loginResp = new LoginResp();
        loginResp.setToken(token);
        return loginResp;
    }
}
