package com.devin.dezhi.ai.strategy.impl;

import com.devin.dezhi.ai.strategy.ModelStrategy;
import com.devin.dezhi.domain.v1.dto.ModelDTO;
import com.devin.dezhi.enums.HttpErrorEnum;
import com.devin.dezhi.enums.ai.ModelProvidersEnum;
import com.devin.dezhi.exception.ModelException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
public class DeepSeekModelStrategy implements ModelStrategy {

    @Override
    public ModelProvidersEnum getModelProvidersEnum() {
        return ModelProvidersEnum.DEEPSEEK;
    }

    @Override
    public Mono<String> chatModel(final String question) {
        // 设置webclient
        WebClient webClient = WebClient.builder()
                .baseUrl("https://api.deepseek.com/chat/completions")
                .defaultHeaders(headers -> {
                    headers.add("Content-Type", "application/json");
                    headers.add("Authorization", "Bearer sk-e668853b54764261a86ba00f0c4b2c3d");
                }).build();

        String requestBody = """
                {
                    "model": "deepseek-chat",
                    "messages": [
                      {"role": "system", "content": "You are a helpful assistant."},
                      {"role": "user", "content": "Hello!"}
                    ],
                    "stream": false
                }
                """;

        return webClient.post()
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new RuntimeException("Error: " + response.statusCode())))
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new RuntimeException("Error: " + response.statusCode())))
                .bodyToMono(String.class);
    }

    /**
     * 测试模型连通性.
     */
    @Override
    public void checkConnectModel(final ModelDTO modelDTO) {
        // 设置webclient
        WebClient webClient = WebClient.builder()
                .baseUrl(modelDTO.getBaseUrl())
                .defaultHeaders(headers -> {
                    headers.add("Content-Type", "application/json");
                    headers.add("Authorization", "Bearer " + modelDTO.getApiKey());
                }).build();

        try {
            webClient.post()
                    .bodyValue(modelDTO.getModelReqBody())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            throw new ModelException(HttpErrorEnum.MODEL_ERROR.getErrCode(), "模型连接异常");
        }
    }
}
