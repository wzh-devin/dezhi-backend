package com.devin.dezhi.ai.chain.handler;

import com.devin.dezhi.ai.chain.ModelContext;
import com.devin.dezhi.ai.chain.AbstractModelHandler;
import com.devin.dezhi.ai.configuration.ToolsRegistry;
import com.devin.dezhi.ai.strategy.ModelStrategy;
import com.devin.dezhi.ai.strategy.ModelStrategyFactory;
import com.devin.dezhi.domain.v1.dto.ModelDTO;
import com.devin.dezhi.domain.v1.dto.ModelResp;
import com.devin.dezhi.domain.v1.entity.ModelManager;
import com.devin.dezhi.enums.ai.ModelProvidersEnum;
import com.devin.dezhi.enums.ai.ModelRoleEnum;
import com.devin.dezhi.model.ModelReqBody;
import org.springframework.stereotype.Component;
import java.util.Objects;

/**
 * 2025/10/7 19:23.
 *
 * <p>
 * 模型调用处理器
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class ModelCallHandler extends AbstractModelHandler {

    private final ModelStrategyFactory modelStrategyFactory;

    private final ToolsRegistry toolsRegistry;

    public ModelCallHandler(final ModelStrategyFactory modelStrategyFactory, final ToolsRegistry toolsRegistry) {
        super();
        this.modelStrategyFactory = modelStrategyFactory;
        this.toolsRegistry = toolsRegistry;
    }

    @Override
    public void handle(final ModelContext context) {

        ModelManager model = context.getModel();

        // 封装模型DTO
        ModelDTO modelDTO = new ModelDTO();
        modelDTO.setProvider(ModelProvidersEnum.valueOf(model.getProvider()));
        modelDTO.setBaseUrl(model.getBaseUrl());
        modelDTO.setApiKey(model.getApiKey());
        modelDTO.setModelReqBody(
                ModelReqBody.builder()
                        .model(model.getModel())
                        .stream(false)
                        .tools(toolsRegistry.getTools())
                        .addMessage(ModelRoleEnum.USER)
                        .content(context.getQuestion())
                        .done()
                        .build());

        context.setModelDTO(modelDTO);

        // 获取策略模型
        ModelStrategy modelStrategy = modelStrategyFactory.getModelStrategy(modelDTO.getProvider());

        // 调用非流式输出
        ModelResp modelResp = modelStrategy.chatModel(modelDTO);

        if (Objects.isNull(modelResp)) {
            // 调用失败，设置结束
            context.setFinished(Boolean.TRUE);
            return;
        }

        // 设置上下文
        context.setModelResp(modelResp);

        // 判断是否需要工具调用
        if (!modelResp.getChoices().isEmpty() && !"stop".equals(modelResp.getChoices().get(0).getFinishReason())) {
            context.setToolCallFlag(Boolean.TRUE);
        }

        // 执行下一个处理器
        invokeNext(context);
    }
}
