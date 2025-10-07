package com.devin.dezhi.model;

import com.devin.dezhi.enums.HttpErrorEnum;
import com.devin.dezhi.enums.ai.ModelRoleEnum;
import com.devin.dezhi.enums.ai.ToolChoiceEnum;
import com.devin.dezhi.exception.ModelException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 2025/9/6 17:34.
 *
 * <p>模型请求体</p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Data
public class ModelReqBody {

    /**
     * 模型名称.
     */
    private String model;

    /**
     * 聊天消息.
     */
    private List<ModelChatMessage> messages;

    /**
     * 流式返回.
     */
    private Boolean stream = true;

    /**
     * 工具列表（用于function calling）.
     */
    private List<Tool> tools;

    /**
     * 工具选择.
     */
    @JsonProperty("tool_choice")
    private ToolChoiceEnum toolChoice = ToolChoiceEnum.AUTO;

    /**
     * 构建器.
     *
     * @return Builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String model;

        private final List<ModelChatMessage> messages = new ArrayList<>();

        private Boolean stream;

        private List<Tool> tools;

        /**
         * 模型名称.
         *
         * @param model 模型名称.
         * @return Builder.
         */
        public Builder model(final String model) {
            this.model = model;
            return this;
        }

        /**
         * 流式返回.
         *
         * @param stream 流式返回.
         * @return Builder.
         */
        public Builder stream(final Boolean stream) {
            this.stream = stream;
            return this;
        }

        /**
         * 添加简单文本消息.
         *
         * @param role    角色.
         * @param content 内容.
         * @return Builder.
         */
        public Builder addMessage(final ModelRoleEnum role, final String content) {
            this.messages.add(new ModelChatMessage(role, null, null, content));
            return this;
        }

        /**
         * 添加消息（使用消息构建器）.
         *
         * @param role 角色.
         * @return MessageBuilder - 支持链式配置消息详情.
         */
        public MessageBuilder addMessage(final ModelRoleEnum role) {
            return new MessageBuilder(this, role);
        }

        /**
         * 批量添加消息.
         *
         * @param messages 聊天消息.
         * @return Builder.
         */
        public Builder addMessages(final List<ModelChatMessage> messages) {
            this.messages.addAll(messages);
            return this;
        }

        /**
         * 设置工具列表.
         *
         * @param tools 工具列表.
         * @return Builder.
         */
        public Builder tools(final List<Tool> tools) {
            this.tools = tools;
            return this;
        }

        /**
         * 添加工具（完整参数）.
         *
         * @param type     工具类型
         * @param function 工具
         * @return Builder.
         */
        public Builder addTool(final String type, final FunctionBody function) {
            if (Objects.isNull(this.tools)) {
                this.tools = new ArrayList<>();
            }
            this.tools.add(Tool.builder().type(type).function(function).build());
            return this;
        }

        /**
         * 添加默认类型 "function" 工具.
         *
         * @param function 工具
         * @return Builder.
         */
        public Builder addFunction(final FunctionBody function) {
            return this.addTool("function", function);
        }

        /**
         * 构建.
         *
         * @return ModelReqBody.
         */
        public ModelReqBody build() {
            if (Objects.isNull(this.model) || this.model.isEmpty()) {
                throw new ModelException(HttpErrorEnum.MODEL_ERROR.getErrCode(), "模型名称不能为空");
            }
            ModelReqBody modelReqBody = new ModelReqBody();
            modelReqBody.setModel(this.model);
            modelReqBody.setMessages(this.messages);
            modelReqBody.setStream(this.stream);
            modelReqBody.setTools(this.tools);
            return modelReqBody;
        }
    }

    /**
     * 消息构建器 - 用于灵活配置单条消息的可选参数.
     */
    public static final class MessageBuilder {

        private final Builder parentBuilder;

        private final ModelRoleEnum role;

        private String toolCallId;

        private Object toolCalls;

        private String content;

        private MessageBuilder(final Builder parentBuilder, final ModelRoleEnum role) {
            this.parentBuilder = parentBuilder;
            this.role = role;
        }

        /**
         * 设置消息内容.
         *
         * @param content 内容.
         * @return MessageBuilder.
         */
        public MessageBuilder content(final String content) {
            this.content = content;
            return this;
        }

        /**
         * 设置工具调用ID（用于tool角色回复）.
         *
         * @param toolCallId 工具调用ID.
         * @return MessageBuilder.
         */
        public MessageBuilder toolCallId(final String toolCallId) {
            this.toolCallId = toolCallId;
            return this;
        }

        /**
         * 设置工具调用列表（用于assistant角色发起工具调用）.
         *
         * @param toolCalls 工具调用.
         * @return MessageBuilder.
         */
        public MessageBuilder toolCalls(final Object toolCalls) {
            this.toolCalls = toolCalls;
            return this;
        }

        /**
         * 完成消息配置，返回主构建器.
         *
         * @return Builder - 回到主构建器继续链式调用.
         */
        public Builder done() {
            this.parentBuilder.messages.add(
                    new ModelChatMessage(this.role, this.toolCallId, this.toolCalls, this.content)
            );
            return this.parentBuilder;
        }
    }

    /**
     * 聊天消息.
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @lombok.Builder
    public static class ModelChatMessage {
        /**
         * 角色.
         */
        private ModelRoleEnum role;

        /**
         * 工具调用ID.
         */
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("tool_call_id")
        private String toolCallId;

        /**
         * 工具调用.
         */
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("tool_calls")
        private Object toolCalls;

        /**
         * 内容.
         */
        private String content;
    }

    @Data
    @lombok.Builder
    @AllArgsConstructor
    public static class Tool {

        /**
         * 工具类型.
         */
        private String type;

        /**
         * 工具.
         */
        private FunctionBody function;
    }
}