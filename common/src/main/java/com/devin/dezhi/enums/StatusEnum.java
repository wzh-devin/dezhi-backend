package com.devin.dezhi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 2025/7/20 20:44.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public enum StatusEnum {

    DRAFT(1, "草稿"),
    IS_PUBLISH(0, "已发布");

    private final Integer status;

    private final String desc;
}
