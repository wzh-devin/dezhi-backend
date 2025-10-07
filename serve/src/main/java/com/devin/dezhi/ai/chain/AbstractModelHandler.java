package com.devin.dezhi.ai.chain;

import java.util.Objects;

/**
 * 2025/10/7 20:41.
 *
 * <p>
 *     抽象模型处理器
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class AbstractModelHandler implements ModelHandler {

    private ModelHandler next;

    @Override
    public void setNext(final ModelHandler next) {
        this.next = next;
    }

    @Override
    public ModelHandler getNext() {
        return this.next;
    }

    /**
     * 继续执行下一个处理器.
     *
     * @param context 上下文
     */
    public void invokeNext(final ModelContext context) {
        if (Objects.isNull(this.next) || !context.getFinished()) {
            this.next.handle(context);
        }
    }
}
