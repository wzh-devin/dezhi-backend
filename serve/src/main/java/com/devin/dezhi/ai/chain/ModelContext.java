package com.devin.dezhi.ai.chain;

import com.devin.dezhi.domain.dto.ModelDTO;
import com.devin.dezhi.domain.dto.ModelResp;
import com.devin.dezhi.domain.entity.ModelManager;
import com.devin.dezhi.model.ModelReqBody;
import lombok.Data;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

/**
 * 2025/10/7 19:24.
 *
 * <p>
 *     模型责任链上下文
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class ModelContext {

    /**
     * 模型DTO.
     */
    private ModelDTO modelDTO;

    /**
     * 模型信息.
     */
    private ModelManager model;

    /**
     * 模型结果.
     */
    private ModelResp modelResp;

    /**
     * 工具调用标识，是否调用工具.
     */
    private Boolean toolCallFlag = Boolean.FALSE;

    /**
     * 当前工具调用次数.
     */
    private Integer currentToolCallTimes = 0;

    /**
     * 最大工具调用次数.
     */
    private Integer maxToolCallTimes = 5;

    /**
     * 模型结果流.
     */
    private Flux<ServerSentEvent<String>> modelStreamResult;

    /**
     * 问题.
     */
    private String question;

    /**
     * 是否完成.
     */
    private Boolean finished = Boolean.FALSE;

    /**
     * 增加工具调用次数.
     */
    public void incrementToolCallTimes() {
        this.currentToolCallTimes++;
    }

    /**
     * 检查是否超过最大工具调用次数.
     * @return true: 超过最大次数，false: 未超过最大次数
     */
    public boolean checkMaxToolCallTimes() {
        return this.currentToolCallTimes >= this.maxToolCallTimes;
    }

    /**
     * 获取模型请求体.
     * @return ModelReqBody
     */
    public ModelReqBody getModelReqBody() {
        return this.modelDTO.getModelReqBody();
    }
}
