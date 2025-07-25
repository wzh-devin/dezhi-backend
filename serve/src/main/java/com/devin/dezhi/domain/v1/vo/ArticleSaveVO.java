package com.devin.dezhi.domain.v1.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;

/**
 * 2025/7/20 17:40.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Data
public class ArticleSaveVO {

    @Schema(description = "文章类别id")
    private Long categoryId;

    @Size(max = 100)
    @Schema(description = "文章标题")
    private String title;

    @Size(max = 255)
    @Schema(description = "文章摘要")
    private String summary;

    @Schema(description = "文章内容")
    private String content;

    @Schema(description = "文章内容md")
    private String contentMd;

    @Schema(description = "文章url")
    private String url;

    @Schema(description = "文章是否置顶")
    private Integer isStick;

    @Schema(description = "文章状态")
    private Integer status;

    @Schema(description = "文章是否AI生成")
    private Integer isAi;

    @Schema(description = "文章标签id列表")
    private List<Long> tagIdList;
}
