// CHECKSTYLE:OFF
package com.devin.dezhi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 2025/4/24 21:36.
 *
 * <p>
 *     主程序入口
 * </p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@SpringBootApplication
@EnableAspectJAutoProxy
public class DeZhiApplication {

    /**
     * 主入口函数.
     * @param args 入口参数
     */
    public static void main(final String[] args) {
        SpringApplication.run(DeZhiApplication.class, args);

        log.info("DeZhi Application Started");
    }
}
// CHECKSTYLE:ON
