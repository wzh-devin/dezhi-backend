package com.devin.dezhi.controller.v1;

import com.devin.dezhi.common.annocation.ApiV1;
import com.devin.dezhi.common.utils.r.ApiResult;
import com.devin.dezhi.domain.v1.vo.MenuVO;
import com.devin.dezhi.service.v1.MenuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * 2025/5/22 1:35.
 *
 * <p>
 *     菜单管理接口
 * </p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@ApiV1
@RestController
@Tag(name = "菜单相关接口")
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    /**
     * 获取菜单数据.
     * @return List
     */
    @GetMapping("")
    public ApiResult<List<MenuVO>> getMenuData() {
        return ApiResult.success(menuService.getMenuData());
    }
}
