package com.devin.dezhi.utils;

import cn.hutool.crypto.digest.DigestUtil;

/**
 * 2025/4/26 16:04.
 *
 * <p>
 *     加密工具类<br>
 *     主要使用md5+盐值进行加密，通过以密钥作为盐值进行加密
 * </p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
public class PasswordEncrypt {

    private final String salt;

    public PasswordEncrypt(final String secretKey) {
        this.salt = secretKey;
    }

    /**
     * 密码加密.
     * @param password 密码
     * @return 加密后的密码
     */
    public String encrypt(final String password) {
        return DigestUtil.md5Hex(password + salt);
    }

    /**
     * 判断密码是否相同.
     * @param password 密码
     * @param encryptPassword 加密后的密码
     * @return Boolean
     */
    public Boolean checkPassword(final String password, final String encryptPassword) {
        return encryptPassword.equals(encrypt(password));
    }
}
