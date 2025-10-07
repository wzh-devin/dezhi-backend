package com.devin.dezhi.controller;

import com.devin.dezhi.annocation.ApiV1;
import com.devin.dezhi.utils.r.ApiResult;
import com.devin.dezhi.domain.vo.DashboardVO;
import com.devin.dezhi.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 2025/8/24 20:50.
 *
 * <p>
 *     控制台相关接口
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@ApiV1
@RestController
@Tag(name = "dashboard")
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    /**
     * 获取仪表盘数据.
     * @return 仪表盘数据
     */
    @GetMapping("")
    @Operation(summary = "获取仪表盘数据")
    public ApiResult<DashboardVO> getDashboardInfo() {
        return ApiResult.success(dashboardService.getDashboardInfo());
    }
}
