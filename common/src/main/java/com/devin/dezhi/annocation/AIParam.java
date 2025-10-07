package com.devin.dezhi.annocation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 2025/10/3 23:15.
 *
 * <p>
 *     模型参数注解
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface AIParam {

    /**
     * 参数类型.
     * @return 参数类型
     */
    String type() default "string";

    /**
     * 参数名称.
     * @return 参数名称
     */
    String name();

    /**
     * 参数描述.
     * @return 参数描述
     */
    String description();

    /**
     * 参数是否必填.
     * @return 参数是否必填
     */
    boolean required() default true;
}
