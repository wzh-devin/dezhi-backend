package com.devin.dezhi.ai.chain.handler;

import com.devin.dezhi.ai.chain.ModelContext;
import com.devin.dezhi.ai.chain.AbstractModelHandler;
import com.devin.dezhi.ai.strategy.ModelStrategy;
import com.devin.dezhi.ai.strategy.ModelStrategyFactory;
import com.devin.dezhi.domain.v1.dto.ModelDTO;
import com.devin.dezhi.enums.ai.ToolChoiceEnum;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * 2025/10/7 21:13.
 *
 * <p>
 *     模型Stream调用处理器
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class ModelChatHandler extends AbstractModelHandler {

    private final ModelStrategyFactory modelStrategyFactory;

    public ModelChatHandler(final ModelStrategyFactory modelStrategyFactory) {
        super();
        this.modelStrategyFactory = modelStrategyFactory;
    }

    @Override
    public void handle(final ModelContext context) {
        ModelDTO modelDTO = context.getModelDTO();
        modelDTO.getModelReqBody().setStream(true);
        clearTools(modelDTO);
        ModelStrategy modelStrategy = modelStrategyFactory.getModelStrategy(modelDTO.getProvider());
        Flux<ServerSentEvent<String>> streamResult = modelStrategy.chatModelStream(modelDTO);
        context.setModelStreamResult(streamResult);
        context.setFinished(true);
    }

    /**
     * 清空工具调用信息.
     * @param modelDTO modelDTO
     */
    private void clearTools(final ModelDTO modelDTO) {
        modelDTO.getModelReqBody().setToolChoice(ToolChoiceEnum.NONE);
        modelDTO.getModelReqBody().setTools(null);
    }
}
