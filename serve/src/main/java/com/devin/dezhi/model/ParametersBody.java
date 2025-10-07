package com.devin.dezhi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

/**
 * 2025/10/3 18:31.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParametersBody {

    /**
     * 参数类型.
     */
    private String type;

    /**
     * 参数属性.
     */
    private Map<String, Property> properties;

    /**
     * 必填参数列表.
     */
    private List<String> required;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Property {

        /**
         * 属性类型.
         */
        private String type;

        /**
         * 属性描述.
         */
        private String description;

        /**
         * 属性枚举列表.
         */
        @JsonProperty("enum")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private List<String> enumList;
    }
}
