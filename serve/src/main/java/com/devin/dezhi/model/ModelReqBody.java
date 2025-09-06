package com.devin.dezhi.model;

import com.devin.dezhi.enums.HttpErrorEnum;
import com.devin.dezhi.exception.ModelException;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 2025/9/6 17:34.
 *
 * <p></p>
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
     * 构建器.
     * @return Builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String model;

        private List<ModelChatMessage> messages = new ArrayList<>();

        private Boolean stream;

        /**
         * 模型名称.
         * @param model 模型名称.
         * @return Builder.
         */
        public Builder model(final String model) {
            this.model = model;
            return this;
        }

        /**
         * 流式返回.
         * @param stream 流式返回.
         * @return Builder.
         */
        public Builder stream(final Boolean stream) {
            this.stream = stream;
            return this;
        }

        /**
         * 添加消息.
         *
         * @param role    角色.
         * @param content 内容.
         * @return Builder.
         */
        public Builder message(final String role, final String content) {
            this.messages.add(new ModelChatMessage(role, content));
            return this;
        }

        /**
         * 添加消息.
         *
         * @param messages 聊天消息.
         * @return Builder.
         */
        public Builder messages(final List<ModelChatMessage> messages) {
            this.messages.addAll(messages);
            return this;
        }

        /**
         * 链式添加消息.
         * @param role 角色.
         * @param content 内容.
         * @return Builder.
         */
        public Builder addMessage(final String role, final String content) {
            if (this.messages == null) {
                this.messages = new ArrayList<>();
            }
            this.messages.add(new ModelChatMessage(role, content));
            return this;
        }

        /**
         * 构建.
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
            return modelReqBody;
        }
    }


    /**
     * 聊天消息.
     */
    @Data
    @AllArgsConstructor
    public static class ModelChatMessage {
        /**
         * 角色.
         */
        private String role;

        /**
         * 内容.
         */
        private String content;
    }
}
