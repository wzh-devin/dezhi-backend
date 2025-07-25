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
    private Integer pageNum;

    @Schema(description = "当前页码的总条数")
    private Integer pageSize;

    @Schema(description = "总记录数")
    private Integer total;

    /**
     * 附加属性.
     *
     * @param page 分页参数
     * @return 附加属性
     */
    public static Addition of(final IPage<?> page) {
        Addition addition = new Addition();
        addition.setTotal(convert(page.getTotal()));
        addition.setPageNum(convert(page.getCurrent()));
        addition.setPageSize(convert(page.getSize()));
        return addition;
    }

    /**
     * 附加属性.
     *
     * @param page 分页参数
     * @return 附加属性
     */
    public static Addition of(final PageResult<?> page) {
        Addition addition = new Addition();
        addition.setTotal(convert(page.getTotal()));
        addition.setPageNum(convert(page.getPageNum()));
        addition.setPageSize(convert(page.getPageSize()));
        return addition;
    }

    /**
     * 转换分页参数格式为int.
     * @param value 值
     * @return 转换后的值
     */
    private static Integer convert(final long value) {
        return value > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) value;
    }
}
