package com.devin.dezhi.utils;

import cn.hutool.core.util.ObjectUtil;
import com.devin.dezhi.exception.BusinessException;
import java.util.Objects;

/**
 * 2025/4/26 0:06.
 *
 * <p>
 *     断言工具类
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
public class AssertUtil {

    /**
     * 如果不是真，则抛异常.
     * @param expression 执行表达式
     * @param msg 错误信息
     */
    public static void isTrue(final boolean expression, final String msg) {
        if (!expression) {
            throwException(msg);
        }
    }

    /**
     * 如果为真，则抛异常.
     * @param expression 执行表达式
     * @param msg 错误信息
     */
    public static void isFalse(final boolean expression, final String msg) {
        if (expression) {
            throwException(msg);
        }
    }

    /**
     * 参数统一性校验.
     * @param o1 对象1
     * @param o2 对象2
     * @param msg 错误信息
     */
    public static void equal(final Object o1, final Object o2, final String msg) {
        if (!Objects.equals(o1, o2)) {
            throwException(msg);
        }
    }

    /**
     * 如果是空对象，则抛异常.
     * @param o 对象
     * @param msg 错误信息
     */
    public static void isEmpty(final Object o, final String msg) {
        if (!ObjectUtil.isEmpty(o)) {
            throwException(msg);
        }
    }

    /**
     * 如果是空对象，则抛异常.
     * @param o 对象
     * @param msg 错误信息
     */
    public static void isNotEmpty(final Object o, final String msg) {
        if (ObjectUtil.isEmpty(o)) {
            throwException(msg);
        }
    }

    private static void throwException(final String errMsg) {
        throw new BusinessException(errMsg);
    }
}
