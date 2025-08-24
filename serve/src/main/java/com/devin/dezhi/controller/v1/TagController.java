package com.devin.dezhi.controller.v1;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.devin.dezhi.common.annocation.ApiV1;
import com.devin.dezhi.common.utils.r.Addition;
import com.devin.dezhi.common.utils.r.ApiResult;
import com.devin.dezhi.domain.v1.entity.Tag;
import com.devin.dezhi.domain.v1.vo.TagQueryVO;
import com.devin.dezhi.domain.v1.vo.TagVO;
import com.devin.dezhi.service.v1.TagService;
import com.devin.dezhi.utils.BeanCopyUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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
 * 2025/7/13 21:57.
 *
 * <p>
 * 标签相关接口
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@ApiV1
@RestController
@io.swagger.v3.oas.annotations.tags.Tag(name = "tag")
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    /**
     * 添加标签.
     *
     * @param tagVO 标签信息
     * @return 添加结果
     */
    @PutMapping("/save")
    @Operation(summary = "添加标签", description = "添加标签")
    public ApiResult<Void> saveTag(@RequestBody final TagVO tagVO) {
        tagService.saveTag(tagVO);
        return ApiResult.success();
    }

    /**
     * 编辑标签.
     *
     * @param tagVO 标签信息
     * @return 编辑结果
     */
    @PostMapping("/edit")
    @Operation(summary = "编辑标签", description = "编辑标签")
    public ApiResult<Void> editTag(@RequestBody final TagVO tagVO) {
        tagService.editTag(tagVO);
        return ApiResult.success();
    }

    /**
     * 删除标签.
     *
     * @param ids 标签id列表
     * @return void
     */
    @PostMapping("/delBatch")
    @Operation(summary = "删除标签", description = "删除标签")
    public ApiResult<Void> delTags(@RequestBody final List<Long> ids) {
        tagService.delTags(ids);
        return ApiResult.success();
    }

    /**
     * 获取标签可选项.
     *
     * @return List
     */
    @GetMapping("/getTagOptional")
    @Operation(summary = "获取标签可选项", description = "获取标签可选项")
    public ApiResult<List<TagVO>> getTagOptional() {
        return ApiResult.success(tagService.getTagOptional());
    }

    /**
     * 分页查询标签.
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @param tagName  标签名称
     * @return List
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询标签", description = "分页查询标签")
    // CHECKSTYLE:OFF
    @Parameters({
            @Parameter(name = "pageNum", description = "页码"),
            @Parameter(name = "pageSize", description = "每页数量"),
            @Parameter(name = "tagName", description = "标签名称")
    })
    // CHECKSTYLE:ON
    public ApiResult<List<TagVO>> page(
            @RequestParam(value = "pageNum", required = false) final Integer pageNum,
            @RequestParam(value = "pageSize", required = false) final Integer pageSize,
            @RequestParam(value = "tagName", required = false) final String tagName
    ) {
        TagQueryVO queryVO = new TagQueryVO();
        queryVO.setPageNum(pageNum);
        queryVO.setPageSize(pageSize);
        queryVO.setName(tagName);
        Page<Tag> page = tagService.page(queryVO);
        List<TagVO> tagVOList = BeanCopyUtils.copy(page.getRecords(), TagVO.class);
        return ApiResult.success(tagVOList, Addition.of(page));
    }
}
