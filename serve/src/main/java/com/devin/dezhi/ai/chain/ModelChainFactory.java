package com.devin.dezhi.ai.chain;

import com.devin.dezhi.ai.chain.handler.FunctionCallHandler;
import com.devin.dezhi.ai.chain.handler.ModelCallHandler;
import com.devin.dezhi.ai.chain.handler.ModelChatHandler;
import org.springframework.stereotype.Component;

/**
 * 2025/10/7 19:20.
 *
 * <p>
 *     模型调用责任链工厂
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class ModelChainFactory {
    private final ModelCallHandler modelCallHandler;

    private final ModelChatHandler modelChatHandler;

    private final FunctionCallHandler functionCallChain;

    public ModelChainFactory(final ModelCallHandler modelCallHandler, final ModelChatHandler modelChatHandler, final FunctionCallHandler functionCallChain) {
        this.modelCallHandler = modelCallHandler;
        this.modelChatHandler = modelChatHandler;
        this.functionCallChain = functionCallChain;
    }

    /**
     * 构建模型调用责任链.
     * @return ModelChain
     */
    public ModelChain build() {
        return ModelChain.build(modelCallHandler, functionCallChain, modelChatHandler);
    }
}
