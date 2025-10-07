package com.devin.dezhi.enums.ai;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 2025/10/8 03:30.
 *
 * <p>
 *     工具选择枚举
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum ToolChoiceEnum {
    /**
     * 模型不要选择任何工具.
     */
    NONE("none"),

    /**
     * 模型自动选择工具.
     */
    AUTO("auto"),

    /**
     * 模型必须选择工具.
     */
    REQUIRED("required");

    private final String value;

    /**
     * 获取值.
     * @return 值
     */
    @JsonValue
    public String getValue() {
        return this.value;
    }
}
