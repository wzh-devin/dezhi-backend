package com.devin.dezhi.ai.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * 2025/10/3 23:49.
 *
 * <p>
 *     工具执行器
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Component
public class ToolExecutor {

    private final ToolsRegistry tools;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public ToolExecutor(final ToolsRegistry toolsRegistry) {
        this.tools = toolsRegistry;
    }

    /**
     * 执行工具调用.
     * @param toolName 工具名称
     * @param argumentsJson 参数
     * @return 执行结果
     */
    public Object executeTool(final String toolName, final String argumentsJson) {
        ToolMetadata metadata = tools.getTool(toolName);
        if (metadata == null) {
            throw new RuntimeException("工具不存在: " + toolName);
        }

        try {
            // 解析参数
            JsonNode argsNode = objectMapper.readTree(argumentsJson);
            Object[] args = buildArguments(metadata, argsNode);

            // 执行方法
            return metadata.getMethod().invoke(metadata.getBean(), args);

        } catch (Exception e) {
            log.error("执行工具失败: {}", toolName, e);
            throw new RuntimeException("工具执行失败: " + e.getMessage());
        }
    }

    private Object[] buildArguments(final ToolMetadata metadata, final JsonNode argsNode) {
        List<ToolMetadata.ParameterMetadata> params = metadata.getParameters();
        Object[] args = new Object[params.size()];

        for (int i = 0; i < params.size(); i++) {
            ToolMetadata.ParameterMetadata param = params.get(i);
            JsonNode valueNode = argsNode.get(param.getName());

            if (valueNode == null || valueNode.isNull()) {
                args[i] = null;
            } else {
                args[i] = convertValue(valueNode, param.getJavaType());
            }
        }

        return args;
    }

    private Object convertValue(final JsonNode node, final Class<?> targetType) {
        if (targetType == String.class) {
            return node.asText();
        } else if (targetType == Integer.class || targetType == int.class) {
            return node.asInt();
        } else if (targetType == Boolean.class || targetType == boolean.class) {
            return node.asBoolean();
        } else if (targetType == Double.class || targetType == double.class) {
            return node.asDouble();
        }
        return node.asText();
    }
}
