package com.devin.dezhi.domain.v1.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

/**
 * 2025/8/24 20:55.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Data
@Schema(description = "仪表盘数据")
public class DashboardVO {

    @Schema(description = "文章数量")
    private Integer articleCount;

    @Schema(description = "发布文章数量")
    private Integer publishArticleCount;

    @Schema(description = "文章列表修改时间前五")
    private List<ArticleVO> articleTop5List;

    @Schema(description = "评论列表前五")
    private List<String> commentTop5List;
}
