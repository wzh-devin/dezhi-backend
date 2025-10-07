package com.devin.dezhi.controller;

import com.devin.dezhi.ai.chain.ModelChain;
import com.devin.dezhi.ai.chain.ModelChainFactory;
import com.devin.dezhi.ai.chain.ModelContext;
import com.devin.dezhi.annocation.ApiV1;
import com.devin.dezhi.dao.ModelManagerDao;
import com.devin.dezhi.domain.entity.ModelManager;
import com.devin.dezhi.enums.HttpErrorEnum;
import com.devin.dezhi.enums.ai.ModelProvidersEnum;
import com.devin.dezhi.exception.ModelException;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import java.util.Objects;

/**
 * 2025/8/25 1:02.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@ApiV1
@RestController
@Tag(name = "ai")
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiController {
    private final ModelManagerDao modelManagerDao;

    private final ModelChainFactory modelChainFactory;

    /**
     * 聊天.
     *
     * @param question      问题
     * @param modelProvider 模型提供商
     * @param modelName     模型名称
     * @return 响应
     */
    @GetMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    // CHECKSTYLE:OFF
    @Parameters(value = {
            @Parameter(name = "question", description = "问题"),
            @Parameter(name = "modelProvider", description = "模型提供者"),
            @Parameter(name = "modelName", description = "模型名称")
    })
    // CHECKSTYLE:ON
    public Flux<ServerSentEvent<String>> chat(
            final String question,
            final ModelProvidersEnum modelProvider,
            final String modelName
    ) {
        // 检查模型是否存在
        // 获取模型
        ModelManager model = modelManagerDao.lambdaQuery()
                .eq(ModelManager::getProvider, modelProvider)
                .eq(ModelManager::getModel, modelName)
                .one();
        if (Objects.isNull(model)) {
            throw new ModelException(HttpErrorEnum.MODEL_ERROR.getErrCode(), "模型未注册");
        }

        // 执行设置模型链
        ModelChain chain = modelChainFactory.build();
        ModelContext context = new ModelContext();
        context.setModel(model);
        context.setQuestion(question);

        return chain.execute(context);
    }
}
