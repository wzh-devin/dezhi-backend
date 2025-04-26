package com.devin.dezhi.exception;

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
public class BusinessException extends RuntimeException {

    public BusinessException(final String message) {
        super(message);
    }

}
