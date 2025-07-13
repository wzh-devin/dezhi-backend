package com.devin.dezhi.domain.v1.vo;

import com.devin.dezhi.result.CommonQueryVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@Schema(description = "类别查询参数")
@EqualsAndHashCode(callSuper = true)
public class CategoryQueryVO extends CommonQueryVO {

    @Schema(description = "类别id")
    private Long id;

    @Schema(description = "类别名称")
    private String name;

    @Schema(description = "类别颜色")
    private String color;
}
