package com.devin.dezhi.common.exception;

import cn.dev33.satoken.exception.NotLoginException;
import com.devin.dezhi.enums.HttpErrorEnum;
import com.devin.dezhi.common.utils.r.ApiResult;
import com.devin.dezhi.exception.BusinessException;
import com.devin.dezhi.exception.FileException;
import com.devin.dezhi.exception.VerifyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    /**
     * 用户未登录.
     * @param e 异常信息
     * @return 响应结果集
     */
    @ExceptionHandler(NotLoginException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ApiResult<?> notLoginException(final NotLoginException e) {
        log.error("用户未登录: {}", e.getMessage());
        return ApiResult.fail(HttpErrorEnum.USER_NOT_LOGIN);
    }

    /**
     * 业务异常.
     * @param e 异常信息
     * @return 响应结果集
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ApiResult<?> businessException(final BusinessException e) {
        log.error("业务异常: {}", e.getMessage());
        return ApiResult.fail(HttpErrorEnum.BUSINESS_ERROR, e.getMessage());
    }

    /**
     * 文件异常.
     * @param e 异常信息
     * @return 响应结果集
     */
    @ExceptionHandler(FileException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ApiResult<?> fileException(final FileException e) {
        log.error("文件异常: {}", e.getMessage());
        return ApiResult.fail(HttpErrorEnum.FILE_ERROR);
    }

    /**
     * 校验异常.
     * @param e 校验异常
     * @return 响应结果集
     */
    @ExceptionHandler(VerifyException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ApiResult<?> fileException(final VerifyException e) {
        log.error("校验异常: {}", e.getMessage());
        return ApiResult.fail(HttpErrorEnum.VERIFY_ERROR, e.getMessage());
    }

    /**
     * 状态异常.
     * @param e 状态异常
     * @return 响应结果集
     */
    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ApiResult<?> illegalStateException(final IllegalStateException e) {
        log.error("请求状态异常: {}", e.getMessage());
        return ApiResult.fail(HttpErrorEnum.ILLEGAL_STATE_ERROR, e.getMessage());
    }
}
