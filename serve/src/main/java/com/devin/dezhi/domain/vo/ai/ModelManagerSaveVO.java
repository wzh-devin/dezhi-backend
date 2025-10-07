package com.devin.dezhi.domain.vo.ai;

import com.devin.dezhi.enums.ai.ModelProvidersEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 2025/8/31 1:39.
 *
 * <p>
 *     模型管理保存VO
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Data
@Schema(description = "模型管理保存VO")
public class ModelManagerSaveVO {

    @NotNull
    @Schema(description = "模型提供商")
    private ModelProvidersEnum provider;

    @NotNull
    @NotBlank
    @Schema(description = "模型名称")
    private String model;

    @Schema(description = "模型基础URL")
    private String baseUrl;

    @NotNull
    @NotBlank
    @Schema(description = "模型API Key")
    private String apiKey;
}
