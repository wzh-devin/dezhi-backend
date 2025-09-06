package com.devin.dezhi.domain.v1.dto;

import com.devin.dezhi.enums.ai.ModelProvidersEnum;
import com.devin.dezhi.model.ModelReqBody;
import lombok.Data;

/**
 * 2025/9/6 16:25.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Data
public class ModelDTO {

    /**
     * 模型基础地址.
     */
    private String baseUrl;

    /**
     * 模型供应商.
     */
    private ModelProvidersEnum provider;

    /**
     * 模型Api Key.
     */
    private String apiKey;

    /**
     * 模型请求体.
     */
    private ModelReqBody modelReqBody;
}
