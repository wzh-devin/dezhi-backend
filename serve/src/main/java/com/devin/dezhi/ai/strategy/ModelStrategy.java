package com.devin.dezhi.ai.strategy;

import com.devin.dezhi.domain.v1.dto.ModelDTO;
import com.devin.dezhi.enums.ai.ModelProvidersEnum;
import reactor.core.publisher.Mono;

/**
 * 2025/8/24 23:24.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
public interface ModelStrategy {

    /**
     * 获取模型提供者枚举.
     *
     * @return 模型提供者枚举
     */
    ModelProvidersEnum getModelProvidersEnum();

    /**
     * 模型聊天.
     *
     * @param question 问题
     * @return 模型回答
     */
    Mono<String> chatModel(String question);

    /**
     * 校验模型连接.
     *
     * @param modelDTO 模型信息
     */
    void checkConnectModel(ModelDTO modelDTO);
}
