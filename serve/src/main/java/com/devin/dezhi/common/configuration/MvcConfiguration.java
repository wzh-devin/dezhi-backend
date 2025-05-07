package com.devin.dezhi.common.configuration;

import com.devin.dezhi.common.annocation.ApiV1;
import com.devin.dezhi.common.interceptor.SaTokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
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
     * 配置路径前缀.
     * @param configurer 路径前缀配置器
     */
    @Override
    public void configurePathMatch(final PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/api/v1", p -> p.isAnnotationPresent(ApiV1.class));
    }
}
