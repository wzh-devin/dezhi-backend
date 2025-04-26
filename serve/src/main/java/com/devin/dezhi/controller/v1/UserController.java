package com.devin.dezhi.controller.v1;

import com.devin.dezhi.common.annocation.ApiV1;
import com.devin.dezhi.common.utils.r.ApiResult;
import com.devin.dezhi.domain.v1.vo.req.UserInfoReq;
import com.devin.dezhi.domain.v1.vo.resp.LoginResp;
import com.devin.dezhi.service.v1.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 2025/4/25 19:32.
 *
 * <p>
 *     用户管理接口
 * </p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@ApiV1
@RestController
@Tag(name = "用户管理相关接口")
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 账号登录.
     * @param userInfoReq 用户登录信息
     * @return LoginResp
     */
    @PostMapping("/loginAccount")
    @Operation(summary = "账密登录", description = "账密登录")
    public ApiResult<LoginResp> loginAccount(@RequestBody final UserInfoReq userInfoReq) {
        return ApiResult.success(userService.loginAccount(userInfoReq));
    }
}
