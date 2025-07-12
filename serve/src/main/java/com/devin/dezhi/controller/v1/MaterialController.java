package com.devin.dezhi.controller.v1;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.devin.dezhi.common.annocation.ApiV1;
import com.devin.dezhi.common.utils.r.Addition;
import com.devin.dezhi.common.utils.r.ApiResult;
import com.devin.dezhi.domain.v1.entity.Material;
import com.devin.dezhi.domain.v1.vo.FileInfoQueryVO;
import com.devin.dezhi.domain.v1.vo.FileInfoVO;
import com.devin.dezhi.enums.FlagEnum;
import com.devin.dezhi.enums.StorageTypeEnum;
import com.devin.dezhi.model.FileInfo;
import com.devin.dezhi.service.v1.MaterialService;
import com.devin.dezhi.utils.BeanCopyUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

/**
 * 2025/6/1 3:14.
 *
 * <p>
 * 文件素材接口
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@ApiV1
@RestController
@Tag(name = "文件素材接口")
@RequestMapping("/material")
@RequiredArgsConstructor
class MaterialController {

    private final MaterialService materialService;

    /**
     * 上传文件素材.
     *
     * @param material 文件素材信息
     * @return 上传之后的文件地址
     */
    @PutMapping("/upload")
    @Operation(summary = "上传文件素材", description = "上传文件素材")
    @Parameter(name = "material", description = "文件素材信息")
    public ApiResult<String> upload(final MultipartFile material) {
        return ApiResult.success(materialService.upload(material));
    }

    /**
     * 文件信息列表.
     *
     * @param pageNum     页码
     * @param pageSize    每页数量
     * @param fileName    文件名称
     * @param storageType 文件存储类型
     * @param status  文件状态
     * @return FileInfoVO
     */
    @GetMapping("/page")
    @Operation(summary = "文件列表", description = "获取文件列表")
    @Parameters({
        @Parameter(name = "pageNum", description = "页码", required = false),
        @Parameter(name = "pageSize", description = "每页数量", required = false),
        @Parameter(name = "fileName", description = "文件名称", required = false),
        @Parameter(name = "storageType", description = "存储类型", required = false),
        @Parameter(name = "status", description = "文件状态", required = true)
    })
    public ApiResult<List<FileInfoVO>> page(
            @RequestParam(value = "pageNum", required = false) final Integer pageNum,
            @RequestParam(value = "pageSize", required = false) final Integer pageSize,
            @RequestParam(value = "fileName", required = false) final String fileName,
            @RequestParam(value = "storageType", required = false) final StorageTypeEnum storageType,
            @RequestParam(value = "status", required = true) final FlagEnum status
    ) {
        FileInfoQueryVO queryVO = new FileInfoQueryVO();
        queryVO.setName(fileName);
        queryVO.setStorageType(Objects.isNull(storageType) ? null : storageType.name());
        queryVO.setPageNum(pageNum);
        queryVO.setPageSize(pageSize);
        queryVO.setStatus(status.name());

        Page<Material> page = materialService.page(queryVO);
        List<FileInfoVO> pageResult = BeanCopyUtils.copy(page.getRecords(), FileInfoVO.class);
        return ApiResult.success(pageResult, Addition.of(page));
    }

    @DeleteMapping("")
    @Operation(summary = "删除文件", description = "通过文件路径删除文件信息")
    @Parameter(name = "pathList", description = "文件路径列表")
    public ApiResult<Void> delMaterial(final @RequestBody List<String> pathList) {
        materialService.delMaterial(pathList);
        return ApiResult.success();
    }
}
