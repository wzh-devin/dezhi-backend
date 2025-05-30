package com.devin.dezhi.common.annocation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;

/**
 * 2025/5/30 18:30.
 *
 * <p>
 * 自定义日志注解
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Logger {

    /**
     * 日志描述.
     * @return String
     */
    String value() default "";

    /**
     * 日志描述.
     * @return String
     */
    String desc() default "";

    /**
     * 模块信息.
     * @return String
     */
    String module() default "";

}
