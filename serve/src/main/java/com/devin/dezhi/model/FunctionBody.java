package com.devin.dezhi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 2025/10/3 17:57.
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
public class FunctionBody {

    /**
     * 工具名称.
     */
    private String name;

    /**
     * 工具描述.
     */
    private String description;

    /**
     * 工具参数.
     */
    private ParametersBody parameters;

    /**
     * 是否严格模式.
     */
    private Boolean strict = true;

}
