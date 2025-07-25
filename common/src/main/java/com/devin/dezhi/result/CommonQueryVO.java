package com.devin.dezhi.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 2025/7/12 18:41.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Data
@Schema(description = "通用查询VO")
public class CommonQueryVO {

    @Schema(description = "页码")
    private Integer pageNum;

    @Schema(description = "每页数量")
    private Integer pageSize;
}
