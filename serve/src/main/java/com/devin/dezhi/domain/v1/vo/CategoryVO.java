package com.devin.dezhi.domain.v1.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 2025/7/13 21:59.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Data
@Schema(description = "类别信息")
public class CategoryVO {

    @Schema(description = "类别id")
    private Long id;

    @Schema(description = "类别名称")
    private String name;

    @Schema(description = "类别颜色")
    private String color;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
