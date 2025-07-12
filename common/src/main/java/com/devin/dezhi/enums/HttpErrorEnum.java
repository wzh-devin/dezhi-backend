package com.devin.dezhi.enums;

import com.devin.dezhi.constant.ErrMsgConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;

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

    /* 系统未知错误 */
    SYSTEM_ERROR(-1, ErrMsgConstant.SYSTEM_ERROR),

    /* 用户错误：2001-2999 */
    USER_NOT_LOGIN(2001, ErrMsgConstant.USER_NOT_LOGIN),
    EMAIL_CODE_INVALID(2002, ErrMsgConstant.EMAIL_CODE_INVALID),

    /* 业务错误：4001-4999 */
    BUSINESS_ERROR(4001, ErrMsgConstant.BUSINESS_ERROR),
    FILE_ERROR(4002, ErrMsgConstant.FILE_ERROR),

    /* 参数错误：5001-5999 */
    VERIFY_ERROR(5001, ErrMsgConstant.VERIFY_ERROR),
    ILLEGAL_STATE_ERROR(5002, ErrMsgConstant.ILLEGAL_STATE_ERROR);

    private final Integer errCode;

    private final String errMsg;
}
