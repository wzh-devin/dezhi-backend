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
public enum DelFlagEnum {

    NORMAL(0, "未删除"),
    IS_DELETED(1, "已删除");

    private final Integer flag;

    private final String desc;

    /**
     * 通过标识名称获取标识.
     * @param flag 状态名称
     * @return 标识
     */
    public static Integer of(final DelFlagEnum flag) {
        for (DelFlagEnum delFlagEnum : DelFlagEnum.values()) {
            if (delFlagEnum.equals(flag)) {
                return delFlagEnum.getFlag();
            }
        }
        return null;
    }
}
