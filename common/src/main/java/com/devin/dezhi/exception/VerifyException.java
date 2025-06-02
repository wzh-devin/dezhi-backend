package com.devin.dezhi.exception;

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
public class VerifyException extends RuntimeException {
    public VerifyException(final String message) {
        super(message);
    }
}
