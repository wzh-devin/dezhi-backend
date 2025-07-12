package com.devin.dezhi.exception;

import com.devin.dezhi.enums.HttpErrorEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 2025/6/2 0:57.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FileException extends RuntimeException {
    private final Integer errCode;

    private final String errMsg;

    public FileException(final String message) {
        super(message);
        this.errCode = HttpErrorEnum.FILE_ERROR.getErrCode();
        this.errMsg = message;
    }

    public FileException(final Integer errCode, final String message) {
        super(message);
        this.errCode = errCode;
        this.errMsg = message;
    }

    public FileException(final HttpErrorEnum httpErrorEnum) {
        super(httpErrorEnum.getErrMsg());
        this.errCode = httpErrorEnum.getErrCode();
        this.errMsg = httpErrorEnum.getErrMsg();
    }
}
