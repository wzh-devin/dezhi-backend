package com.devin.dezhi.common.utils.r;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 2025/7/12 16:30.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Data
@Schema(description = "附加信息")
public class Addition {

    @Schema(description = "当前页码")
    private Long pageNum;

    @Schema(description = "当前页码的总条数")
    private Long pageSize;

    @Schema(description = "总记录数")
    private Long total;

    /**
     * 附加属性.
     *
     * @param page 分页参数
     * @return 附加属性
     */
    public static Addition of(final IPage<?> page) {
        Addition addition = new Addition();
        addition.setTotal(page.getTotal());
        addition.setPageNum(page.getCurrent());
        addition.setPageSize(page.getSize());
        return addition;
    }
}
