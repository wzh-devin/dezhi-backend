package com.devin.dezhi.exception;

import com.devin.dezhi.enums.HttpErrorEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 2025/9/6 18:33.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ModelException extends RuntimeException {

    private final Integer errCode;

    private final String errMsg;

    public ModelException(final String message) {
        super(message);
        this.errCode = HttpErrorEnum.MODEL_ERROR.getErrCode();
        this.errMsg = message;
    }

    public ModelException(final Integer errCode, final String errMsg) {
        super(errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public ModelException(final HttpErrorEnum httpErrorEnum) {
        super(httpErrorEnum.getErrMsg());
        this.errCode = httpErrorEnum.getErrCode();
        this.errMsg = httpErrorEnum.getErrMsg();
    }
}
