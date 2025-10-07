package com.devin.dezhi.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

/**
 * 2025/10/3 20:15.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class Choices {

    /**
     * choice索引.
     */
    private Integer index;

    /**
     * finish_reason.
     */
    @JsonProperty("finish_reason")
    private String finishReason;

    /**
     * message.
     */
    private Message message;

    @Data
    public static class Message {
        /**
         * 角色.
         */
        private String role;

        /**
         * 内容.
         */
        private String content;

        /**
         * 工具调用.
         */
        @JsonProperty("tool_calls")
        private List<ToolCall> toolCalls;
    }

    @Data
    public static class ToolCall {
        /**
         * 工具调用id.
         */
        private String id;

        /**
         * 工具调用类型.
         */
        private String type;

        /**
         * 工具调用参数.
         */
        private Function function;
    }

    @Data
    public static class Function {
        /**
         * 工具调用名称.
         */
        private String name;

        /**
         * 函数参数.
         */
        private String arguments;
    }
}
