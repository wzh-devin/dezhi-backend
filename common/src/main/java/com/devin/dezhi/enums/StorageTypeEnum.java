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

    MINIO;

    /**
     * 根据名称获取枚举.
     * @param name 枚举名称
     * @return StorageTypeEnum
     */
    public static StorageTypeEnum of(final String name) {
        for (StorageTypeEnum value : values()) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        return null;
    }
}
