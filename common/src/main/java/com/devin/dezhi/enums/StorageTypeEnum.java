package com.devin.dezhi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 2025/6/2 1:40.
 *
 * <p>
 * 存储类型枚举
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public enum StorageTypeEnum {

    MINIO("MINIO", "minio存储");

    /**
     * 存储类型.
     */
    private final String type;

    /**
     * 描述.
     */
    private final String desc;
}
