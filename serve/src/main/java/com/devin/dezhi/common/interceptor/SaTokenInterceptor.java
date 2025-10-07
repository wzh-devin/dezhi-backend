package com.devin.dezhi.common.interceptor;

import cn.dev33.satoken.exception.BackResultException;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.StopMatchException;
import cn.dev33.satoken.fun.SaParamFunction;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.strategy.SaAnnotationStrategy;
import cn.hutool.json.JSONUtil;
import com.devin.dezhi.constant.RedisKey;
import com.devin.dezhi.domain.entity.user.User;
import com.devin.dezhi.utils.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 2025/4/19 18:05.
 *
 * <p>
 * 对SaInterceptor进行更细化的重写<br>
 * 这里只是对拦截的路径新增了一个日志打印，以及Redis的缓存策略<br>
 * 如果有更精细化操作，也可以自定义重写
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Component
public class SaTokenInterceptor extends SaInterceptor {

    @Autowired
    private RedisUtil redisUtil;

    public SaTokenInterceptor() {
        // 返回自定义的策略
        super.auth = handler -> {
            // 判断是否登录
            StpUtil.checkLogin();

            // 从Redis中获取数据
            String uid = (String) StpUtil.getLoginIdDefaultNull();
            String userKey = RedisKey.generateRedisKey(RedisKey.LOGIN_INFO, uid);
            User user = JSONUtil.toBean(redisUtil.get(userKey), User.class);

            // 如果没有用户信息，则证明没有登录或者登录过期，需要重新登录
            if (Objects.isNull(user)) {
                throw new NotLoginException("登录已过期", null, null);
            }
        };
    }

    public SaTokenInterceptor(final SaParamFunction<Object> auth) {
        super.auth = auth;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        try {
            // 开放浏览器的预检请求路径
            if ("OPTIONS".equals(request.getMethod())) {
                return true;
            }
            String requestPath = request.getRequestURI();
            log.info("拦截的请求路径为：{}", requestPath);

            // ✅ 新增：过滤异常路径，避免拦截JSON响应或错误页面
            if (shouldSkipIntercept(request, requestPath)) {
                log.debug("跳过拦截，路径：{}", requestPath);
                return true;
            }

            if (this.isAnnotation && handler instanceof HandlerMethod) {
                Method method = ((HandlerMethod) handler).getMethod();
                SaAnnotationStrategy.instance.checkMethodAnnotation.accept(method);
            }

            this.auth.run(handler);
        } catch (StopMatchException ignored) {
        } catch (BackResultException e) {
            if (response.getContentType() == null) {
                response.setContentType("text/plain; charset=utf-8");
            }

            response.getWriter().print(e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * 判断是否需要跳过拦截.
     *
     * @param request     请求
     * @param requestPath 请求路径
     * @return boolean
     */
    private boolean shouldSkipIntercept(final HttpServletRequest request, final String requestPath) {
        // 1. 跳过错误页面
        if (requestPath.startsWith("/error")) {
            return true;
        }

        // 2. 跳过包含JSON格式的路径（异常响应被误当作路径）
        if (requestPath.contains("{") || requestPath.contains("}")) {
            return true;
        }

        // 3. 跳过非正常请求分发类型
        if (request.getDispatcherType() != jakarta.servlet.DispatcherType.REQUEST) {
            return true;
        }

        // 4. 跳过静态资源
        if (requestPath.matches(".+\\.(js|css|png|jpg|jpeg|gif|ico|woff|woff2|ttf|svg)$")) {
            return true;
        }

        // 5. 跳过健康检查等路径
        return requestPath.startsWith("/actuator")
                || requestPath.startsWith("/health")
                || requestPath.startsWith("/favicon.ico");
    }
}
