package com.devin.dezhi.common.configuration;

import com.devin.dezhi.common.annocation.ApiV1;
import com.devin.dezhi.common.interceptor.SaTokenInterceptor;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.math.BigInteger;
import java.util.List;

/**
 * 2025/4/25 10:53.
 *
 * <p></p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private SaTokenInterceptor saTokenInterceptor;

    /**
     * 配置跨域请求.
     * @param registry 跨域注册器
     */
    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("*");
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        // 配置swagger排除路径
        List<String> swaggerExcludePathPatterns = List.of(
                "/doc.html",
                "/webjars/**",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/favicon.ico",
                "/error"
        );
        // 配置项目排除路径
        List<String> dezhiExcludePathPatterns = List.of(
                "/**/loginAccount",
                "/**/loginEmail",
                "/**/getEmailCode",
                "/**/signup",
                "/**/forgetPassword"
        );

        // 开启Sa-Token的路由检验
        registry.addInterceptor(saTokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(dezhiExcludePathPatterns)
                .excludePathPatterns(swaggerExcludePathPatterns);
    }

    /**
     * jackson序列化时将Long->String.
     * @return ObjectMapper
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            SimpleModule simpleModule = new SimpleModule();
            // simpleModule.addSerializer(long.class, ToStringSerializer.instance);
            simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
            simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
            simpleModule.addSerializer(BigInteger.class, ToStringSerializer.instance);
            builder.modules(new Jdk8Module(), new JavaTimeModule(), simpleModule);
        };
    }

    /**
     * 配置路径前缀.
     * @param configurer 路径前缀配置器
     */
    @Override
    public void configurePathMatch(final PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/api/v1", p -> p.isAnnotationPresent(ApiV1.class));
    }
}
