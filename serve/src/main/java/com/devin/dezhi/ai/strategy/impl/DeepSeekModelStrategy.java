package com.devin.dezhi.ai.strategy.impl;

import com.devin.dezhi.ai.configuration.ToolExecutor;
import com.devin.dezhi.ai.configuration.ToolsRegistry;
import com.devin.dezhi.ai.strategy.ModelStrategy;
import com.devin.dezhi.dao.v1.ModelManagerDao;
import com.devin.dezhi.domain.v1.dto.ModelDTO;
import com.devin.dezhi.enums.HttpErrorEnum;
import com.devin.dezhi.enums.ai.ModelProvidersEnum;
import com.devin.dezhi.exception.ModelException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
public class DeepSeekModelStrategy implements ModelStrategy {

    private final ModelManagerDao modelManagerDao;

    private final ToolsRegistry toolsRegistry;

    private final ToolExecutor toolExecutor;

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
//        modelDTO.setModelReqBody(ModelReqBody.builder()
//                .model("deepseek-chat")
//                .stream(false)
//                .message("user", "今天上海天气怎么样？")
//                .tools(toolsRegistry.getTools())
//                .build());
//
//        WebClient webClient = WebClient.builder()
//                .baseUrl(modelDTO.getBaseUrl())
//                .defaultHeaders(headers -> {
//                    headers.add("Content-Type", "application/json");
//                    headers.add("Authorization", "Bearer " + modelDTO.getApiKey());
//                }).build();
//
//        // 大模型返回需要的工具列表
//        ModelResp modelResp = webClient.post()
//                .bodyValue(modelDTO.getModelReqBody())
//                .retrieve()
//                .bodyToMono(ModelResp.class)
//                .block();
//
//        Choices.Message message = modelResp.getChoices().get(0).getMessage();
//
//        List<Choices.ToolCall> toolCalls = message.getToolCalls();
//        Choices.ToolCall toolCall = toolCalls.get(0);
//
//        ModelReqBody modelReqBody = modelDTO.getModelReqBody();
//        ModelReqBody.ModelChatMessage modelChatMessage = new ModelReqBody.ModelChatMessage();
//        BeanUtils.copyProperties(message, modelChatMessage);
//        modelReqBody.getMessages().add(modelChatMessage);
//
//        modelReqBody.getMessages().add(new ModelReqBody.ModelChatMessage(
//                "tool",
//                toolCall.getId(),
//                null,
//                toolExecutor.executeTool(toolCall.getFunction().getName(), toolCall.getFunction().getArguments()).toString()
//        ));
//
//        String block = webClient.post()
//                .bodyValue(modelDTO.getModelReqBody())
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();
//
//        log.info("block: {}", block);
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
