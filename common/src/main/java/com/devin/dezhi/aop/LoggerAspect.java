package com.devin.dezhi.aop;

import com.alibaba.fastjson2.JSON;
import com.devin.dezhi.annocation.Logger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * 2025/5/30 18:11.
 *
 * <p>
 * 日志切面类
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Aspect
@Component
public class LoggerAspect {

    /**
     * 切面.
     */
    @Pointcut("execution (* com.devin.dezhi..controller..*.*(..)) || @annotation(com.devin.dezhi.annocation.Logger)")
    public void pointCut() {
    }

    /**
     * 环绕通知.
     *
     * @param joinPoint 切点
     * @return Object
     */
    @SneakyThrows
    @Around("pointCut()")
    public Object aroundAdvice(final ProceedingJoinPoint joinPoint) {
        // 开始时间
        long startTime = System.currentTimeMillis();

        // 执行方法
        Object result = joinPoint.proceed();

        // 结束时间
        long endTime = System.currentTimeMillis();

        // 日志记录
        recordLog(joinPoint, endTime - startTime, result);

        return result;
    }

    /**
     * 日志记录.
     *
     * @param joinPoint 切点
     * @param execTime  方法执行时间
     * @param result    方法返回值
     */
    private void recordLog(final ProceedingJoinPoint joinPoint, final long execTime, final Object result) {
        // 获取类名
        String className = joinPoint.getTarget().getClass().getName();

        // 获取方法签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        // 获取方法指标
        Object[] args = joinPoint.getArgs();
        Method method = methodSignature.getMethod();
        String resultStr = JSON.toJSONString(result);

        // 获取方法注解
        Logger logger = method.getAnnotation(Logger.class);
        String module = Optional.ofNullable(logger)
                .map(Logger::module)
                .orElse("");
        String desc = Optional.ofNullable(logger)
                .map(l -> {
                    if (Objects.equals(l.value(), "")) {
                        return l.desc();
                    } else {
                        return l.value();
                    }
                }).orElse("");

        // 日志预设值
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = Optional.ofNullable(requestAttributes)
                .map(ServletRequestAttributes::getRequest)
                .orElseThrow();

        // 生成日志信息
        log.info("************************************************************************************************************");
        log.info("【请求时间】===> {}", time);
        log.info("【请求地址】===> {}", getIpAddr(httpServletRequest));
        log.info("【请求方法】===> {}", className.concat(".").concat(method.getName()).concat("()"));
        log.info("【请求参数】===> {}", args);
        if (!Objects.equals(module, "")) {
            log.info("【模块信息】===> {}", module);
        }
        if (!Objects.equals(desc, "")) {
            log.info("【方法描述】===> {}", desc);
        }
        log.info("【返回参数】===> {}", resultStr);
        log.info("【执行时间】===> {} ms", execTime);
        log.info("************************************************************************************************************");
    }

    /**
     * 获取请求IP地址.
     *
     * @param request HttpServletRequest
     * @return String
     */
    private String getIpAddr(final HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }
}
