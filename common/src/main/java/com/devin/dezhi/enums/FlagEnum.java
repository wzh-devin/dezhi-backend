package com.devin.dezhi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 2025/4/27 2:23.
 *
 * <p>
 *     标识枚举
 * </p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public enum FlagEnum {

    DISABLED(0, "禁用"),
    NORMAL(1, "正常");

    private final Integer flag;

    private final String desc;
}
