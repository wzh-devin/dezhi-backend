package com.devin.dezhi.ai.chain;

import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

/**
 * 2025/10/7 19:20.
 *
 * <p>
 * 模型调用链
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public record ModelChain(ModelHandler modelHandler) {

    /**
     * 执行模型调用链.
     *
     * @param context 上下文
     */
    public Flux<ServerSentEvent<String>> execute(final ModelContext context) {
        while (!context.getFinished() && !context.checkMaxToolCallTimes()) {
            // 执行处理器
            modelHandler.handle(context);

            // 判断是否需要工具调用
            if (context.getToolCallFlag()) {
                context.setToolCallFlag(Boolean.FALSE);
            } else {
                // 不需要工具调用，结束循环
                break;
            }
        }

        return context.getModelStreamResult();
    }

    /**
     * 构建模型调用链.
     *
     * @param handlers 处理器列表
     * @return ModelChain
     */
    public static ModelChain build(final ModelHandler... handlers) {
        if (handlers == null || handlers.length == 0) {
            throw new IllegalArgumentException("处理器列表不能为空");
        }

        // 循环顺序构建调用链
        for (int i = 0; i < handlers.length - 1; i++) {
            // 设置下一个处理器
            handlers[i].setNext(handlers[i + 1]);
        }

        // 返回头部处理器
        return new ModelChain(handlers[0]);
    }
}
