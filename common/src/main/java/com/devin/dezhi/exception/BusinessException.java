package com.devin.dezhi.exception;

import com.devin.dezhi.enums.HttpErrorEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 2025/4/26 0:19.
 *
 * <p>
 *     业务异常
 * </p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException {

    private final Integer errCode;

    private final String errMsg;

    public BusinessException(final String message) {
        super(message);
        this.errCode = HttpErrorEnum.BUSINESS_ERROR.getErrCode();
        this.errMsg = message;
    }

    public BusinessException(final Integer errCode, final String message) {
        super(message);
        this.errCode = errCode;
        this.errMsg = message;
    }

    public BusinessException(final HttpErrorEnum httpErrorEnum) {
        super(httpErrorEnum.getErrMsg());
        this.errCode = httpErrorEnum.getErrCode();
        this.errMsg = httpErrorEnum.getErrMsg();
    }

}
