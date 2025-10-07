package com.devin.dezhi.ai.strategy;

import com.devin.dezhi.domain.dto.ModelDTO;
import com.devin.dezhi.domain.dto.ModelResp;
import com.devin.dezhi.enums.ai.ModelProvidersEnum;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

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
     * 模型聊天（流式）.
     *
     * @param modelDTO 模型DTO
     * @return 模型回答
     */
    Flux<ServerSentEvent<String>> chatModelStream(ModelDTO modelDTO);

    /**
     * 模型聊天（非流式）- 用于Function Calling.
     *
     * @param modelDTO 模型DTO
     * @return 模型响应
     */
    ModelResp chatModel(ModelDTO modelDTO);

    /**
     * 校验模型连接.
     *
     * @param modelDTO 模型信息
     */
    void checkConnectModel(ModelDTO modelDTO);
}
