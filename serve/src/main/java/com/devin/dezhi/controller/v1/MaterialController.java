package com.devin.dezhi.controller.v1;

import com.devin.dezhi.common.annocation.ApiV1;
import com.devin.dezhi.common.utils.r.ApiResult;
import com.devin.dezhi.service.v1.MaterialService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 2025/6/1 3:14.
 *
 * <p>
 *     文件素材接口
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

    @PutMapping("/upload")
    public ApiResult<String> upload(final MultipartFile material) {
        return ApiResult.success(materialService.upload(material));
    }
}
