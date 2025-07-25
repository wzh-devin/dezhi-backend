package com.devin.dezhi.controller.v1;

import com.devin.dezhi.common.annocation.ApiV1;
import com.devin.dezhi.common.utils.r.Addition;
import com.devin.dezhi.common.utils.r.ApiResult;
import com.devin.dezhi.common.utils.r.PageResult;
import com.devin.dezhi.domain.v1.vo.ArticleQueryVO;
import com.devin.dezhi.domain.v1.vo.ArticleSaveVO;
import com.devin.dezhi.domain.v1.vo.ArticleUpdateVO;
import com.devin.dezhi.domain.v1.vo.ArticleVO;
import com.devin.dezhi.enums.DelFlagEnum;
import com.devin.dezhi.enums.StatusEnum;
import com.devin.dezhi.result.CommonDeleteVO;
import com.devin.dezhi.service.v1.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * 2025/7/13 23:36.
 *
 * <p>
 * 文章相关接口
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@ApiV1
@RestController
@Tag(name = "article")
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    /**
     * 添加文章.
     *
     * @param articleSaveVO 文章保存参数
     * @return void
     */
    @GetMapping("/save")
    @Operation(summary = "添加文章", description = "添加文章")
    public ApiResult<Void> saveArticle(@RequestBody @Valid final ArticleSaveVO articleSaveVO) {
        articleService.saveArticle(articleSaveVO);
        return ApiResult.success();
    }

    /**
     * 批量删除文章.
     *
     * @param deleteVO 文章ID列表
     * @return void
     */
    @PostMapping("/delArticleBatch")
    @Operation(summary = "批量删除文章", description = "批量删除文章")
    public ApiResult<Void> delArticle(@RequestBody final CommonDeleteVO deleteVO) {
        articleService.delArticle(deleteVO.getIdList());
        return ApiResult.success();
    }

    /**
     * 批量恢复文章.
     *
     * @param commonVO 批量恢复文章ID列表
     * @return void
     */
    @PostMapping("/recoverArticle")
    @Operation(summary = "批量恢复文章", description = "批量恢复文章")
    public ApiResult<Void> recoverArticle(@RequestBody final CommonDeleteVO commonVO) {
        articleService.recoverArticle(commonVO.getIdList());
        return ApiResult.success();
    }

    /**
     * 清空回收站.
     *
     * @param deleteVO 批量删除文章ID列表
     * @return void
     */
    @PostMapping("/cleanRecycle")
    @Operation(summary = "清空回收站", description = "清空回收站")
    public ApiResult<Void> cleanRecycle(@RequestBody final CommonDeleteVO deleteVO) {
        articleService.cleanRecycle(deleteVO.getIdList());
        return ApiResult.success();
    }

    /**
     * 编辑文章.
     *
     * @param articleUpdateVO 文章更新参数
     * @return void
     */
    @PostMapping("/editArticle")
    @Operation(summary = "编辑文章", description = "编辑文章")
    public ApiResult<Void> editArticle(@RequestBody @Valid final ArticleUpdateVO articleUpdateVO) {
        articleService.editArticle(articleUpdateVO);
        return ApiResult.success();
    }

    /**
     * 获取文章详情.
     * @param articleId 文章ID
     * @return 文章详情
     */
    @GetMapping("/getArticleInfo/{articleId}")
    @Operation(summary = "获取文章详情", description = "获取文章详情")
    public ApiResult<ArticleVO> getArticleInfo(@PathVariable("articleId") final Long articleId) {
        return ApiResult.success(articleService.getArticleInfo(articleId));
    }

    /**
     * 分页查询文章.
     *
     * @param pageNum      页码
     * @param pageSize     每页数量
     * @param title        文章标题
     * @param categoryName 文章类别
     * @param status       文章状态
     * @param delFlag      删除状态
     * @return 文章列表
     */
    @GetMapping("/page")
    // CHECKSTYLE:OFF
    @Parameters({
            @Parameter(name = "pageNum", description = "页码", required = false),
            @Parameter(name = "pageSize", description = "每页数量", required = false),
            @Parameter(name = "title", description = "文章标题", required = false),
            @Parameter(name = "categoryName", description = "文章类别", required = false),
            @Parameter(name = "status", description = "文章状态", required = true),
            @Parameter(name = "delFlag", description = "删除状态", required = true)
    })
    // CHECKSTYLE:ON
    @Operation(summary = "分页查询文章", description = "分页查询文章")
    public ApiResult<List<ArticleVO>> page(
            @RequestParam(value = "pageNum", required = false) final Integer pageNum,
            @RequestParam(value = "pageSize", required = false) final Integer pageSize,
            @RequestParam(value = "title", required = false) final String title,
            @RequestParam(value = "categoryName", required = false) final String categoryName,
            @RequestParam(value = "status", required = true) final StatusEnum status,
            @RequestParam(value = "delFlag", required = true) final DelFlagEnum delFlag
    ) {
        ArticleQueryVO queryVO = new ArticleQueryVO();
        queryVO.setPageNum(pageNum);
        queryVO.setPageSize(pageSize);
        queryVO.setTitle(title);
        queryVO.setCategoryName(categoryName);
        queryVO.setStatus(status);
        queryVO.setDelFlag(delFlag);

        PageResult<ArticleVO> pageResult = articleService.page(queryVO);
        Addition addition = Addition.of(pageResult);
        return ApiResult.success(pageResult.getRecords(), addition);
    }
}
