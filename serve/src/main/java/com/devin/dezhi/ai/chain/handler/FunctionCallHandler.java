package com.devin.dezhi.ai.chain.handler;

import com.devin.dezhi.ai.chain.ModelContext;
import com.devin.dezhi.ai.chain.AbstractModelHandler;
import com.devin.dezhi.ai.configuration.ToolExecutor;
import com.devin.dezhi.domain.dto.Choices;
import com.devin.dezhi.domain.dto.ModelResp;
import com.devin.dezhi.enums.ai.ModelRoleEnum;
import com.devin.dezhi.model.ModelReqBody;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * 2025/10/7 20:47.
 *
 * <p>
 *     模型工具调用执行器
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class FunctionCallHandler extends AbstractModelHandler {
    private final ToolExecutor toolExecutor;

    public FunctionCallHandler(final ToolExecutor toolExecutor) {
        super();
        this.toolExecutor = toolExecutor;
    }

    @Override
    public void handle(final ModelContext context) {
        // 如果不需要工具调用 || 调用工具超过最大限制，则直接跳过执行下一个处理器
        if (!context.getToolCallFlag() || context.checkMaxToolCallTimes()) {
            context.setToolCallFlag(Boolean.FALSE);
            invokeNext(context);
            return;
        }

        context.incrementToolCallTimes();

        // 获取模型调用响应
        ModelResp modelResp = context.getModelResp();
        Choices.Message message = modelResp.getChoices().get(0).getMessage();
        List<Choices.ToolCall> toolCalls = message.getToolCalls();

        // 将返回的历史信息，添加到请求体中
        ModelReqBody.ModelChatMessage assistantMessage = new ModelReqBody.ModelChatMessage();
        BeanUtils.copyProperties(message, assistantMessage);
        context.getModelReqBody().getMessages().add(assistantMessage);

        // 执行方法调用结果
        toolCalls.forEach(toolCall -> {
            String toolName = toolCall.getFunction().getName();
            String arguments = toolCall.getFunction().getArguments();

            // 工具函数执行
            Object result = toolExecutor.executeTool(toolName, arguments);
            ModelReqBody.ModelChatMessage tool = ModelReqBody.ModelChatMessage.builder()
                    .role(ModelRoleEnum.TOOL)
                    .toolCallId(toolCall.getId())
                    .content(result.toString())
                    .build();

            context.getModelReqBody().getMessages().add(tool);
        });

        invokeNext(context);
    }
}
