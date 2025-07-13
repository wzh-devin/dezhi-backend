package com.devin.dezhi.enums;

/**
 * 2025/7/13 14:53.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
public enum FileTypeEnum {

    /**
     * jpg图片.
     */
    JPG,

    /**
     * png图片.
     */
    PNG,

    /**
     * gif图片.
     */
    GIF;

    /**
     * 根据名称获取枚举.
     * @param name 枚举名称
     * @return FileTypeEnum
     */
    public static FileTypeEnum of(final String name) {
        for (FileTypeEnum value : values()) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        return null;
    }
}
