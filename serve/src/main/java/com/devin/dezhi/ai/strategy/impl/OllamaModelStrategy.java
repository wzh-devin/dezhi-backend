package com.devin.dezhi.ai.strategy.impl;

import com.devin.dezhi.enums.ai.ModelProvidersEnum;
import org.springframework.stereotype.Component;

/**
 * 2025/10/7 22:52.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class OllamaModelStrategy extends AbstractOpenAIModelStrategy {
    @Override
    public ModelProvidersEnum getModelProvidersEnum() {
        return ModelProvidersEnum.OLLAMA;
    }
}
