package com.devin.dezhi.common.utils.r;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

/**
 * 2025/6/2 15:21.
 *
 * <p>
 * 分页响应结果
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Data
@Schema(description = "分页响应结果集")
public class PageResult<T> {

    /**
     * 当前页码.
     */
    @Schema(description = "当前页码")
    private Long pageNum;

    /**
     * 总页码.
     */
    @Schema(description = "总页码")
    private Long pageSize;

    /**
     * 总记录数.
     */
    @Schema(description = "总记录数")
    private Long total;

    /**
     * 总数据.
     */
    @Schema(description = "总数据")
    private List<T> dataList;
}
