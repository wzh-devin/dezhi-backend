package com.devin.dezhi.enums.ai;

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
public enum ToolChoiceEnum {
    /**
     * 模型不要选择任何工具.
     */
    NONE,

    /**
     * 模型自动选择工具.
     */
    AUTO,

    /**
     * 模型必须选择工具.
     */
    REQUIRED
}
