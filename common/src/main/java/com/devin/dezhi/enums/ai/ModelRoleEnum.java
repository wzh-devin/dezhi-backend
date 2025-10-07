package com.devin.dezhi.enums.ai;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 2025/10/8 03:33.
 *
 * <p>
 *     模型角色枚举
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum ModelRoleEnum {

    /**
     * 系统角色.
     */
    SYSTEM("system"),

    /**
     * 用户角色.
     */
    USER("user"),

    /**
     * 助手角色.
     */
    ASSISTANT("assistant"),

    /**
     * 工具角色.
     */
    TOOL("tool");

    private final String value;

    /**
     * 获取枚举值.
     *
     * @return 枚举值
     */
    @JsonValue
    public String getValue() {
        return value;
    }

    /**
     * 根据枚举值获取枚举.
     * @param value 枚举值
     * @return 枚举
     */
    public static ModelRoleEnum of(final String value) {
        for (ModelRoleEnum role : ModelRoleEnum.values()) {
            if (role.getValue().equals(value)) {
                return role;
            }
        }
        return null;
    }
}
