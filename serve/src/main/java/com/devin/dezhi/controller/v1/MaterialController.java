package com.devin.dezhi.controller.v1;

import com.devin.dezhi.common.annocation.ApiV1;
import com.devin.dezhi.common.utils.r.ApiResult;
import com.devin.dezhi.common.utils.r.PageResult;
import com.devin.dezhi.domain.v1.dto.FileInfoDTO;
import com.devin.dezhi.domain.v1.vo.FileInfoVO;
import com.devin.dezhi.service.v1.MaterialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

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
     * @param fileInfoDTO 文件信息参数
     * @return FileInfoVO
     */
    @GetMapping("/list")
    @Operation(summary = "文件列表", description = "获取文件列表")
    @Parameter(name = "fileInfoDTO", description = "文件信息")
    public ApiResult<PageResult<FileInfoVO>> list(final FileInfoDTO fileInfoDTO) {
        return ApiResult.success(materialService.list(fileInfoDTO));
    }

    @DeleteMapping("")
    @Operation(summary = "删除文件", description = "通过文件路径删除文件信息")
    @Parameter(name = "pathList", description = "文件路径列表")
    public ApiResult<Void> delMaterial(final @RequestBody List<String> pathList) {
        materialService.delMaterial(pathList);
        return ApiResult.success();
    }
}
