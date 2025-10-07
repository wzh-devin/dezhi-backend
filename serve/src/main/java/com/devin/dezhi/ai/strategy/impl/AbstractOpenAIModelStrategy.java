package com.devin.dezhi.ai.strategy.impl;

import com.alibaba.fastjson2.JSONObject;
import com.devin.dezhi.ai.strategy.ModelStrategy;
import com.devin.dezhi.domain.v1.dto.ModelDTO;
import com.devin.dezhi.domain.v1.dto.ModelResp;
import com.devin.dezhi.enums.HttpErrorEnum;
import com.devin.dezhi.exception.ModelException;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

/**
 * 2025/10/7 22:23.
 *
 * <p>
 *     OPENAI规范抽象策略类
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class AbstractOpenAIModelStrategy implements ModelStrategy {

    @Override
    public Flux<ServerSentEvent<String>> chatModelStream(final ModelDTO modelDTO) {
        return getWebClient(modelDTO.getBaseUrl(), modelDTO.getApiKey())
                .post()
                .bodyValue(modelDTO.getModelReqBody())
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(String.class)
                .filter(line -> !line.isEmpty() && !line.equals("data: [DONE]"))
                .map(line -> {
                    return ServerSentEvent.<String>builder().data(line).build();
                });
    }

    @Override
    public ModelResp chatModel(final ModelDTO modelDTO) {
        String jsonString = JSONObject.toJSONString(modelDTO.getModelReqBody());
        return getWebClient(modelDTO.getBaseUrl(), modelDTO.getApiKey())
                .post()
                .bodyValue(modelDTO.getModelReqBody())
                .retrieve()
                .bodyToMono(ModelResp.class)
                .block();
    }

    @Override
    public void checkConnectModel(final ModelDTO modelDTO) {
        try {
            chatModel(modelDTO);
        } catch (Exception e) {
            throw new ModelException(HttpErrorEnum.MODEL_ERROR.getErrCode(), "模型连接异常");
        }
    }

    /**
     * 获取WebClient.
     * @param baseUrl baseUrl
     * @param apiKey apiKey
     * @return WebClient
     */
    public WebClient getWebClient(final String baseUrl, final String apiKey) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeaders(headers -> {
                    headers.add("Content-Type", "application/json");
//                    headers.add("Authorization", "Bearer " + apiKey);
                }).build();
    }
}
