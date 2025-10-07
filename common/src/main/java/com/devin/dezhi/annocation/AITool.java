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
 *     模型工具注解
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AITool {

    /**
     * 工具名称.
     * @return 工具名称
     */
    String name();

    /**
     * 工具描述.
     * @return 工具描述
     */
    String description();

    /**
     * 模型工具参数.
     * @return 模型工具参数
     */
    AIParam[] params() default {};
}
