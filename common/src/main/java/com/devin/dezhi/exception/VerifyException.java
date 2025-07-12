package com.devin.dezhi.exception;

import com.devin.dezhi.enums.HttpErrorEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 2025/6/2 3:02.
 *
 * <p>
 * 验证异常
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class VerifyException extends RuntimeException {

    private final Integer errCode;

    private final String errMsg;

    public VerifyException(final String message) {
        super(message);
        this.errCode = HttpErrorEnum.VERIFY_ERROR.getErrCode();
        this.errMsg = message;
    }

    public VerifyException(final Integer errCode, final String message) {
        super(message);
        this.errCode = errCode;
        this.errMsg = message;
    }

    public VerifyException(final HttpErrorEnum httpErrorEnum) {
        super(httpErrorEnum.getErrMsg());
        this.errCode = httpErrorEnum.getErrCode();
        this.errMsg = httpErrorEnum.getErrMsg();
    }
}
