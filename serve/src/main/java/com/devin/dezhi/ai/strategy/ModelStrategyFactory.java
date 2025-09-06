package com.devin.dezhi.ai.strategy;

import com.devin.dezhi.enums.ai.ModelProvidersEnum;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 2025/8/24 23:16.
 *
 * <p>
 * 模型策略工厂类
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Component
public class ModelStrategyFactory {

    private final Map<ModelProvidersEnum, ModelStrategy> modelStrategyMap;

    public ModelStrategyFactory(final List<ModelStrategy> modelStrategyList) {
        this.modelStrategyMap = modelStrategyList.stream()
                .collect(
                        Collectors.toMap(
                                ModelStrategy::getModelProvidersEnum,
                                Function.identity()
                        )
                );
    }

    /**
     * 生产模型策略.
     *
     * @param modelProvidersEnum 模型提供者枚举
     * @return 模型策略
     */
    public ModelStrategy getModelStrategy(final ModelProvidersEnum modelProvidersEnum) {
        return modelStrategyMap.get(modelProvidersEnum);
    }
}
