package com.devin.dezhi.constant;

/**
 * 2025/4/26 17:07.
 *
 * <p>
 *     Redis常量Key
 * </p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
public class RedisKey {

    public static final String LOGIN_INFO = "userLogin:uid_%s";

    public static final String USER_ROLE = "userRole:uid_%s";

    public static final String USER_PERMISSION = "userPermission:uid_%s";

    private static final String BASE_KEY = "dezhi:";

    /**
     * 生成RedisKey.
     * @param key RedisKey
     * @param args 填写的参数
     * @return RedisKey
     */
    public static String generateRedisKey(final String key, final Object... args) {
        if (args.length == 0) {
            return BASE_KEY.concat(key).replace("%s", "");
        }
        return String.format(BASE_KEY.concat(key), args);
    }
}
