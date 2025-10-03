package com.devin.dezhi.ai.strategy.impl;

import com.devin.dezhi.ai.strategy.ModelStrategy;
import com.devin.dezhi.dao.v1.ModelManagerDao;
import com.devin.dezhi.domain.v1.dto.ModelDTO;
import com.devin.dezhi.enums.HttpErrorEnum;
import com.devin.dezhi.enums.ai.ModelProvidersEnum;
import com.devin.dezhi.exception.ModelException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

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

    @Autowired
    private ModelManagerDao modelManagerDao;

    @Override
    public ModelProvidersEnum getModelProvidersEnum() {
        return ModelProvidersEnum.DEEPSEEK;
    }

    @Override
    public Flux<ServerSentEvent<String>> chatModel(final ModelDTO modelDTO) {
        // 设置webclient
        WebClient webClient = WebClient.builder()
                .baseUrl(modelDTO.getBaseUrl())
                .defaultHeaders(headers -> {
                    headers.add("Content-Type", "application/json");
                    headers.add("Authorization", "Bearer " + modelDTO.getApiKey());
                }).build();

        return webClient.post()
                .bodyValue(modelDTO.getModelReqBody())
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(String.class)
                .filter(line -> !line.isEmpty() && !line.equals("data: [DONE]"))
                .map(line -> {
                    return ServerSentEvent.<String>builder().data(line).build();
                });
    }

//    public void sendFunctionCall() {
//        // 获取模型
//        ModelManager model = modelManagerDao.lambdaQuery()
//                .eq(ModelManager::getProvider, ModelProvidersEnum.DEEPSEEK)
//                .eq(ModelManager::getModel, "deepseek-chat")
//                .one();
//        if (Objects.isNull(model)) {
//            throw new ModelException(HttpErrorEnum.MODEL_ERROR.getErrCode(), "模型未注册");
//        }
//        // 封装模型DTO
//        ModelDTO modelDTO = new ModelDTO();
//        modelDTO.setProvider(ModelProvidersEnum.DEEPSEEK);
//        modelDTO.setBaseUrl(model.getBaseUrl());
//        modelDTO.setApiKey(model.getApiKey());
//
//        FunctionBody func01 = FunctionBody.builder()
//                .name("get_weather")
//                .description("获取天气信息")
//                .parameters(
//                        ParametersBody.builder()
//                                .type("object")
//                                .properties(
//                                        Map.of(
//                                                "city",
//                                                ParametersBody.Property.builder()
//                                                        .type("string")
//                                                        .description("城市名称")
//                                                        .build()
//                                        )
//                                )
//                                .required(List.of("city"))
//                                .build())
//                .build();
//
//        FunctionBody func02 = FunctionBody.builder()
//                .name("get_user")
//                .description("获取用户信息")
//                .parameters(
//                        ParametersBody.builder()
//                                .type("object")
//                                .properties(
//                                        Map.of(
//                                                "username",
//                                                ParametersBody.Property.builder()
//                                                        .type("string")
//                                                        .description("用户信息")
//                                                        .build()
//                                        )
//                                )
//                                .required(List.of("username"))
//                                .build())
//                .build();
//
//        modelDTO.setModelReqBody(ModelReqBody.builder()
//                .model("deepseek-chat")
//                .stream(false)
//                .message("user", "今天上海天气怎么样？")
//                .functions(func01)
//                .functions(func02)
//                .build());
//
//        WebClient webClient = WebClient.builder()
//                .baseUrl(modelDTO.getBaseUrl())
//                .defaultHeaders(headers -> {
//                    headers.add("Content-Type", "application/json");
//                    headers.add("Authorization", "Bearer " + modelDTO.getApiKey());
//                }).build();
//
//        log.info("模型请求：{}", modelDTO.getModelReqBody());
//
//        // 大模型返回需要的工具列表
//        ModelResp modelResp = webClient.post()
//                .bodyValue(modelDTO.getModelReqBody())
//                .retrieve()
//                .bodyToMono(ModelResp.class)
//                .block();
//
//        log.info("模型返回：{}", modelResp);
//    }

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
