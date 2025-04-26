package com.devin.dezhi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 2025/4/26 0:13.
 *
 * <p>
 *     业务异常枚举
 * </p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public enum BusinessErrorEnum {

    SYSTEM_ERROR(5000, "系统异常"),
    BUSINESS_ERROR(5001, "{0}");

    /**
     * 错误码.
     */
    private final Integer errCode;

    /**
     * 错误信息.
     */
    private final String errMsg;
}
