package com.devin.dezhi.common.configuration;

import com.devin.dezhi.utils.PasswordEncrypt;
import com.devin.dezhi.utils.SnowFlake;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 2025/4/25 22:43.
 *
 * <p>
 *     公共配置类，主要是为common包提供支持
 * </p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Configuration
public class CommonConfiguration {

    @Value("${dezhi.secret-key}")
    private String secretKey;

    /**
     * 配置雪花算法，单数据中心、单机器模式.
     * @return SnowFlake
     */
    @Bean
    public SnowFlake snowFlake() {
        return new SnowFlake(0, 0);
    }

    /**
     * 配置密码加密工具.
     * @return PasswordEncrypt
     */
    @Bean
    public PasswordEncrypt passwordEncrypt() {
        return new PasswordEncrypt(secretKey);
    }
}
