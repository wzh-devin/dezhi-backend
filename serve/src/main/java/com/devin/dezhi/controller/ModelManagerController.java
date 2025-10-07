package com.devin.dezhi.controller;

import com.devin.dezhi.annocation.ApiV1;
import com.devin.dezhi.utils.r.ApiResult;
import com.devin.dezhi.domain.vo.ai.ModelManagerSaveVO;
import com.devin.dezhi.domain.vo.ai.ModelManagerVO;
import com.devin.dezhi.result.CommonDeleteVO;
import com.devin.dezhi.service.ModelManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * 2025/8/31 1:18.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@ApiV1
@RestController
@Tag(name = "model")
@RequestMapping("/model")
@RequiredArgsConstructor
public class ModelManagerController {
    private final ModelManagerService modelManagerService;

    /**
     * 获取模型列表.
     * @return 模型列表
     */
    @GetMapping("/getModelList")
    @Operation(summary = "获取模型列表")
    public ApiResult<List<ModelManagerVO>> getModelList() {
        return ApiResult.success(modelManagerService.getModelList());
    }

    /**
     * 保存模型.
     * @param modelManagerSaveVO 模型保存信息
     * @return 保存结果
     */
    @PostMapping("/saveModel")
    @Operation(summary = "保存模型")
    public ApiResult<Void> saveModel(@RequestBody @Valid final ModelManagerSaveVO modelManagerSaveVO) {
        modelManagerService.saveModel(modelManagerSaveVO);
        return ApiResult.success();
    }

    /**
     * 删除模型.
     * @param deleteVO 删除信息
     * @return 删除结果
     */
    @PostMapping("/delModel")
    @Operation(summary = "删除模型")
    public ApiResult<Void> delModel(@RequestBody final CommonDeleteVO deleteVO) {
        modelManagerService.delModel(deleteVO.getIdList());
        return ApiResult.success();
    }
}
