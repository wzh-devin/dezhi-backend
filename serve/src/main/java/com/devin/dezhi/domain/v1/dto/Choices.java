package com.devin.dezhi.domain.v1.dto;

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

    private Integer index;

    @JsonProperty("finish_reason")
    private String finishReason;

    private Message message;

    @Data
    public static class Message {
        private String role;

        private String content;

        @JsonProperty("tool_calls")
        private List<ToolCall> toolCalls;
    }

    @Data
    public static class ToolCall {
        private String id;

        private String type;

        private Function function;
    }

    @Data
    public static class Function {
        private String name;

        private String arguments;
    }
}
