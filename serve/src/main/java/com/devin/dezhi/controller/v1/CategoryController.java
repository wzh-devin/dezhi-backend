package com.devin.dezhi.controller.v1;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.devin.dezhi.common.annocation.ApiV1;
import com.devin.dezhi.common.utils.r.Addition;
import com.devin.dezhi.common.utils.r.ApiResult;
import com.devin.dezhi.domain.v1.vo.CategoryQueryVO;
import com.devin.dezhi.domain.v1.vo.CategoryVO;
import com.devin.dezhi.service.v1.CategoryService;
import com.devin.dezhi.utils.BeanCopyUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * 2025/7/13 23:36.
 *
 * <p>
 * 分类相关接口
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@ApiV1
@RestController
@Tag(name = "category")
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    /**
     * 添加类别.
     *
     * @param categoryVO 类别信息
     * @return 添加结果
     */
    @PutMapping("/save")
    @Operation(summary = "添加类别", description = "添加类别")
    public ApiResult<Void> saveCategory(@RequestBody final CategoryVO categoryVO) {
        categoryService.saveCategory(categoryVO);
        return ApiResult.success();
    }

    /**
     * 删除类别.
     *
     * @param ids 类别id列表
     * @return void
     */
    @PostMapping("/delBatch")
    @Operation(summary = "删除类别", description = "删除类别")
    public ApiResult<Void> delCategories(@RequestBody final List<Long> ids) {
        categoryService.delCategories(ids);
        return ApiResult.success();
    }

    /**
     * 编辑类别.
     *
     * @param categoryVO 类别信息
     * @return 编辑结果
     */
    @PostMapping("/edit")
    @Operation(summary = "编辑类别", description = "编辑类别")
    public ApiResult<Void> editCategory(@RequestBody final CategoryVO categoryVO) {
        categoryService.editCategory(categoryVO);
        return ApiResult.success();
    }

    /**
     * 分页查询类别.
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @param categoryName  类别名称
     * @return List
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询类别", description = "分页查询类别")
    // CHECKSTYLE:OFF
    @Parameters({
            @Parameter(name = "pageNum", description = "页码"),
            @Parameter(name = "pageSize", description = "每页数量"),
            @Parameter(name = "categoryName", description = "类别名称")
    })
    // CHECKSTYLE:ON
    public ApiResult<List<CategoryVO>> page(
            @RequestParam(value = "pageNum", required = false) final Integer pageNum,
            @RequestParam(value = "pageSize", required = false) final Integer pageSize,
            @RequestParam(value = "categoryName", required = false) final String categoryName
    ) {
        CategoryQueryVO queryVO = new CategoryQueryVO();
        queryVO.setPageNum(pageNum);
        queryVO.setPageSize(pageSize);
        queryVO.setName(categoryName);
        Page<com.devin.dezhi.domain.v1.entity.Category> page = categoryService.page(queryVO);
        List<CategoryVO> categoryVOList = BeanCopyUtils.copy(page.getRecords(), CategoryVO.class);
        return ApiResult.success(categoryVOList, Addition.of(page));
    }
}
