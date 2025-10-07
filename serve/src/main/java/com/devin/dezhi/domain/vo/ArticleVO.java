package com.devin.dezhi.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;
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
public class ArticleVO {

    @Schema(description = "文章id")
    private Long id;

    @Schema(description = "文章类别信息")
    private CategoryVO category;

    @Schema(description = "文章标题")
    private String title;

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

    @Schema(description = "文章是否热门")
    private Integer isHot;

    @Schema(description = "文章是否AI生成")
    private Integer isAi;

    @Schema(description = "文章创建时间")
    private LocalDateTime createTime;

    @Schema(description = "文章更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "文章标签列表")
    private List<TagVO> tagList;
}
