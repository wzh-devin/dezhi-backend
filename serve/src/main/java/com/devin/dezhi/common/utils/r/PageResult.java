package com.devin.dezhi.common.utils.r;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.Collections;
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
    private List<T> records;

    /**
     * 创建分页结果.
     *
     * @param page 分页对象
     * @param <T>  数据类型
     * @return 分页结果
     */
    public static <T> PageResult<T> of(final Page<T> page) {
        PageResult<T> result = new PageResult<>();
        result.setPageNum(page.getCurrent());
        result.setPageSize(page.getSize());
        result.setTotal(page.getTotal());
        result.setRecords(page.getRecords());
        return result;
    }

    /**
     * 构造方法.
     *
     * @param page    分页对象
     * @param records 数据列表
     * @param <T>     数据类型
     * @return PageResult
     */
    public static <T> PageResult<T> of(final List<T> records, final Page<?> page) {
        PageResult<T> result = new PageResult<>();
        result.setPageNum(page.getCurrent());
        result.setPageSize(page.getSize());
        result.setTotal(page.getTotal());
        result.setRecords(records);
        return result;
    }

    /**
     * 创建分页结果.
     *
     * @param page 分页对象
     * @param <T>  数据类型
     * @return 分页结果
     */
    public static <T> PageResult<T> ofEmpty(final Page<?> page) {
        PageResult<T> result = new PageResult<>();
        result.setPageNum(page.getCurrent());
        result.setPageSize(page.getSize());
        result.setTotal(0L);
        result.setRecords(Collections.emptyList());
        return result;
    }
}
