package com.devin.dezhi.controller.v1;

import com.devin.dezhi.ai.strategy.ModelStrategyFactory;
import com.devin.dezhi.common.annocation.ApiV1;
import com.devin.dezhi.enums.ai.ModelProvidersEnum;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

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
    private final ModelStrategyFactory modelStrategyFactory;

    /**
     * 聊天.
     * @param question  问题
     * @return 响应
     */
    @GetMapping("/chat")
    public Mono<String> chat(final String question) {
        return modelStrategyFactory.getModelStrategy(ModelProvidersEnum.DEEPSEEK).chatModel(null);
    }
}
