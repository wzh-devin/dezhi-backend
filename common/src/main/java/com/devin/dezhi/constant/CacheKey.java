package com.devin.dezhi.constant;

/**
 * 2025/4/27 20:30.
 *
 * <p>
 *     缓存Key，Caffeine等
 * </p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
public class CacheKey {

    public static final String EMAIL_CODE_KEY = "email_%s";

    /**
     * 生成缓存Key.
     * @param key key
     * @param args 参数
     * @return 缓存Key
     */
    public static String generateKey(final String key, final Object... args) {
        return String.format(key, args);
    }
}
