package com.devin.dezhi.domain.v1.vo.ai;

import com.devin.dezhi.enums.ai.ModelProvidersEnum;
import lombok.Data;
import java.util.List;

/**
 * 2025/9/6 20:04.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Data
public class ModelManagerVO {

    /**
     * 模型提供商.
     */
    private ModelProvidersEnum provider;

    /**
     * 模型列表.
     */
    private List<Model> models;

    @Data
    public static class Model {

        /**
         * 模型ID.
         */
        private Long id;

        /**
         * 模型名称.
         */
        private String model;

        /**
         * 模型基础URL.
         */
        private String baseUrl;
    }
}
