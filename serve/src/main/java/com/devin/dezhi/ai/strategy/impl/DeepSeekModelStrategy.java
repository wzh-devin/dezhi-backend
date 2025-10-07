package com.devin.dezhi.ai.strategy.impl;

import com.devin.dezhi.enums.ai.ModelProvidersEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 2025/8/24 23:29.
 *
 * <p>
 * deepseek模型策略
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DeepSeekModelStrategy extends AbstractOpenAIModelStrategy {

    @Override
    public ModelProvidersEnum getModelProvidersEnum() {
        return ModelProvidersEnum.DEEPSEEK;
    }
}
