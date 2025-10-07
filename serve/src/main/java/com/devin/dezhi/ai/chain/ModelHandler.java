package com.devin.dezhi.ai.chain;

/**
 * 2025/10/7 19:23.
 *
 * <p>
 *     模型处理器接口
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public interface ModelHandler {

    /**
     * 模型处理函数.
     * @param context 责任链上下文
     */
    void handle(ModelContext context);

    /**
     * 设置下一个处理器.
     * @param next 下一个处理器
     */
    void setNext(ModelHandler next);

    /**
     * 获取下一个处理器.
     * @return 下一个处理器
     */
    ModelHandler getNext();
}
