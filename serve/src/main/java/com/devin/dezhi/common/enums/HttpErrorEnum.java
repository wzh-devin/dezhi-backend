package com.devin.dezhi.common.enums;

import com.alibaba.fastjson2.JSONObject;
import com.devin.dezhi.common.utils.r.ApiResult;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.io.IOException;
import java.text.MessageFormat;

/**
 * 2025/4/27 1:24.
 *
 * <p>
 *     错误码枚举
 * </p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public enum HttpErrorEnum {

    SYSTEM_ERROR(500, "系统异常: {0}"),
    NOT_LOGIN(403, "用户未登录: {0}"),
    BUSINESS_ERROR(501, "业务异常: {0}");

    private final Integer errCode;

    private final String errMsg;

    /**
     * 向前端发送错误信息.
     * @param response 响应对象
     * @param args 参数
     * @throws IOException io异常
     */
    public void sendHttpError(final HttpServletResponse response, final Object... args) throws IOException {
        response.setStatus(errCode);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSONObject.toJSONString(ApiResult.fail(errCode, MessageFormat.format(errMsg, args))));
    }
}
