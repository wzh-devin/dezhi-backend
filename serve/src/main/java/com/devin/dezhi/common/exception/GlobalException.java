package com.devin.dezhi.common.exception;

import cn.dev33.satoken.exception.NotLoginException;
import com.devin.dezhi.common.enums.HttpErrorEnum;
import com.devin.dezhi.exception.BusinessException;
import com.devin.dezhi.exception.FileException;
import com.devin.dezhi.exception.VerifyException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.io.IOException;

/**
 * 2025/4/26 17:24.
 *
 * <p>
 *     全局异常处理类
 * </p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalException {

    private final HttpServletResponse response;

    /**
     * 用户未登录.
     * @param e 异常信息
     */
    @ExceptionHandler(NotLoginException.class)
    public void notLoginException(final NotLoginException e) throws IOException {
        log.error("用户未登录: {}", e.getMessage());
        HttpErrorEnum.NOT_LOGIN.sendHttpError(response, "登录身份无效，请重新进行登录");
    }

    /**
     * 业务异常.
     * @param e 异常信息
     */
    @ExceptionHandler(BusinessException.class)
    public void businessException(final BusinessException e) throws IOException {
        log.error("业务异常: {}", e.getMessage());
        HttpErrorEnum.BUSINESS_ERROR.sendHttpError(response, e.getMessage());
    }

    /**
     * 文件异常.
     * @param e 异常信息
     */
    @ExceptionHandler(FileException.class)
    public void fileException(final FileException e) throws IOException {
        log.error("文件异常: {}", e.getMessage());
        HttpErrorEnum.BUSINESS_ERROR.sendHttpError(response, e.getMessage());
    }

    /**
     * 校验异常.
     * @param e 校验异常
     */
    @ExceptionHandler(VerifyException.class)
    public void fileException(final VerifyException e) throws IOException {
        log.error("校验异常: {}", e.getMessage());
        HttpErrorEnum.VERIFY_ERROR.sendHttpError(response, e.getMessage());
    }
}
