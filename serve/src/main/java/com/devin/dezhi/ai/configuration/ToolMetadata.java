package com.devin.dezhi.ai.configuration;

import lombok.Builder;
import lombok.Data;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 2025/10/3 23:33.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@Builder
public class ToolMetadata {

    /**
     * 工具名称.
     */
    private String name;

    /**
     * 工具描述.
     */
    private String description;

    /**
     * 是否严格模式.
     */
    private Boolean strict;

    /**
     * 工具方法.
     */
    private Method method;

    /**
     * 工具bean.
     */
    private Object bean;

    /**
     * 工具参数.
     */
    private List<ParameterMetadata> parameters;

    @Data
    @Builder
    public static class ParameterMetadata {

        /**
         * 参数类型.
         */
        private String type;

        /**
         * 参数名称.
         */
        private String name;

        /**
         * 参数描述.
         */
        private String description;

        /**
         * 是否必填.
         */
        private boolean required;

        /**
         * 参数java类型.
         */
        private Class<?> javaType;
    }
}
