package com.devin.dezhi.ai.configuration;

import com.devin.dezhi.common.annocation.AIParam;
import com.devin.dezhi.common.annocation.AITool;
import com.devin.dezhi.model.FunctionBody;
import com.devin.dezhi.model.ModelReqBody;
import com.devin.dezhi.model.ParametersBody;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 2025/10/3 23:25.
 *
 * <p>
 * 自动注册工具类
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Component
public class ToolsRegistry implements ApplicationListener<ContextRefreshedEvent> {

    private static final Map<String, ToolMetadata> TOOLS = new ConcurrentHashMap<>();

    private final ApplicationContext applicationContext;

    public ToolsRegistry(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 监听容器启动完成事件.
     *
     * @param event 容器启动完成事件
     */
    @Override
    public void onApplicationEvent(@NotNull final ContextRefreshedEvent event) {
        // 项目启动，扫描注册所有的工具
        scanTools();
    }

    /**
     * 扫描工具类.
     */
    private void scanTools() {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(Component.class);

        beans.forEach((beanName, bean) -> {
            Class<?> clazz = bean.getClass();

            // 处理 CGLIB 代理类
            if (clazz.getName().contains("$$")) {
                clazz = clazz.getSuperclass();
            }

            for (Method method : clazz.getDeclaredMethods()) {
                AITool aiTool = method.getAnnotation(AITool.class);
                if (aiTool != null) {
                    ToolMetadata metadata = buildToolMetadata(aiTool, method, bean);
                    TOOLS.put(metadata.getName(), metadata);
                    log.info("注册工具: {} - {}", metadata.getName(), metadata.getDescription());
                }
            }
        });
    }

    /**
     * 构建工具元数据.
     *
     * @param aiTool 注解
     * @param method 方法
     * @param bean   bean
     * @return ToolMetadata
     */
    private ToolMetadata buildToolMetadata(final AITool aiTool, final Method method, final Object bean) {
        List<ToolMetadata.ParameterMetadata> parameters = new ArrayList<>();

        Parameter[] methodParams = method.getParameters();
        for (Parameter param : methodParams) {
            AIParam aiParam = param.getAnnotation(AIParam.class);
            if (aiParam != null) {
                parameters.add(ToolMetadata.ParameterMetadata.builder()
                        .name(aiParam.name())
                        .description(aiParam.description())
                        .type(aiParam.type().isEmpty() ? inferType(param.getType()) : aiParam.type())
                        .required(aiParam.required())
                        .javaType(param.getType())
                        .build());
            }
        }

        return ToolMetadata.builder()
                .name(aiTool.name())
                .description(aiTool.description())
                .method(method)
                .bean(bean)
                .parameters(parameters)
                .build();
    }

    /**
     * 推断参数类型.
     *
     * @param clazz 类
     * @return String
     */
    private String inferType(final Class<?> clazz) {
        if (clazz == String.class) {
            return "string";
        }
        if (clazz == Integer.class || clazz == int.class) {
            return "integer";
        }
        if (clazz == Boolean.class || clazz == boolean.class) {
            return "boolean";
        }
        if (clazz == Double.class || clazz == double.class) {
            return "number";
        }
        if (clazz.isEnum()) {
            return "enum";
        }
        if (clazz.isArray()) {
            return "array";
        }
        return "object";
    }

    /**
     * 获取所有工具元数据.
     *
     * @return List
     */
    public List<ToolMetadata> getAllToolsMetadata() {
        return new ArrayList<>(TOOLS.values());
    }

    /**
     * 根据名称获取工具.
     *
     * @param name 工具名称
     * @return ToolMetadata
     */
    public ToolMetadata getTool(final String name) {
        return TOOLS.get(name);
    }

    /**
     * 转换为模型请求体.
     *
     * @return List
     */
    public List<ModelReqBody.Tool> getTools() {
        return TOOLS.values().stream()
                .map(this::convertToTool)
                .collect(Collectors.toList());
    }

    private ModelReqBody.Tool convertToTool(final ToolMetadata metadata) {
        Map<String, ParametersBody.Property> properties = new HashMap<>();
        List<String> required = new ArrayList<>();

        for (ToolMetadata.ParameterMetadata param : metadata.getParameters()) {
            properties.put(param.getName(), ParametersBody.Property.builder()
                    .type(param.getType())
                    .description(param.getDescription())
                    .build());

            if (param.isRequired()) {
                required.add(param.getName());
            }
        }

        return ModelReqBody.Tool.builder()
                .type("function")
                .function(
                        FunctionBody.builder()
                                .name(metadata.getName())
                                .description(metadata.getDescription())
                                .parameters(
                                        ParametersBody.builder()
                                                .type("object")
                                                .properties(properties)
                                                .required(required)
                                                .build())
                                .strict(true)
                                .build())
                .build();
    }
}
