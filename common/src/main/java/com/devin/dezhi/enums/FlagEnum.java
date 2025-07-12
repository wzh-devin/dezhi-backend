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

    /**
     * 通过标识名称获取标识.
     * @param flagName 标识名称
     * @return 标识
     */
    public static Integer of(final String flagName) {
        for (FlagEnum flagEnum : FlagEnum.values()) {
            if (flagEnum.name().equals(flagName)) {
                return flagEnum.getFlag();
            }
        }
        return null;
    }
}
