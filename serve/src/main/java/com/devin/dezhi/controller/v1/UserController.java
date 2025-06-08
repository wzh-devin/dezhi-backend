package com.devin.dezhi.controller.v1;

import cn.dev33.satoken.stp.StpUtil;
import com.devin.dezhi.common.annocation.ApiV1;
import com.devin.dezhi.common.utils.r.ApiResult;
import com.devin.dezhi.domain.v1.dto.UserInfoDTO;
import com.devin.dezhi.domain.v1.vo.user.LoginVO;
import com.devin.dezhi.domain.v1.vo.user.UserInfoVO;
import com.devin.dezhi.service.v1.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
     * @param userInfoDTO 用户登录信息
     * @return LoginResp
     */
    @PostMapping("/loginAccount")
    @Operation(summary = "账密登录", description = "账密登录")
    public ApiResult<LoginVO> loginAccount(@RequestBody final UserInfoDTO userInfoDTO) {
        return ApiResult.success(userService.loginAccount(userInfoDTO));
    }

    /**
     * 用户登出.
     * @param uid 用户id
     * @return Void
     */
    @GetMapping("/logout")
    @Operation(summary = "用户登出", description = "用户登出")
    public ApiResult<Void> logout() {
        userService.logout();
        return ApiResult.success();
    }

    /**
     * 用户注销.
     * @param uid 用户id
     * @return Void
     */
    @DeleteMapping("/deregisterAccount")
    @Operation(summary = "用户注销", description = "用户注销")
    @Parameter(name = "uid", description = "用户id")
    public ApiResult<Void> deregisterAccount(@RequestParam("uid") final Long uid) {
        userService.deregisterAccount(uid);
        return ApiResult.success();
    }

    /**
     * 获取邮箱验证码.
     * @param email 邮箱
     * @return Void
     */
    @GetMapping("/getEmailCode")
    @Operation(summary = "获取邮箱验证码", description = "获取邮箱验证码")
    @Parameter(name = "email", description = "用户邮箱")
    public ApiResult<Void> getEmailCode(@RequestParam("email") final String email) {
        userService.getEmailCode(email);
        return ApiResult.success();
    }

    /**
     * 邮箱验证码登录.
     * @param userInfoDTO 用户登录信息
     * @return LoginResp
     */
    @PostMapping("/loginEmail")
    @Operation(summary = "邮箱验证码登录", description = "邮箱验证码登录")
    public ApiResult<LoginVO> loginEmail(@RequestBody final UserInfoDTO userInfoDTO) {
        return ApiResult.success(userService.loginEmail(userInfoDTO));
    }

    /**
     * 用户注册.
     * @param userInfoDTO 用户信息
     * @return Void
     */
    @PutMapping("/signup")
    @Operation(summary = "用户注册", description = "用户注册")
    public ApiResult<Void> signup(@RequestBody final UserInfoDTO userInfoDTO) {
        userService.signup(userInfoDTO);
        return ApiResult.success();
    }

    /**
     * 忘记密码.
     * @param userInfoDTO 用户信息
     * @return Void
     */
    @PostMapping("/forgetPassword")
    @Operation(summary = "忘记密码", description = "忘记密码")
    public ApiResult<Void> forgetPassword(@RequestBody final UserInfoDTO userInfoDTO) {
        userService.forgetPassword(userInfoDTO);
        return ApiResult.success();
    }

    /**
     * 获取登录用户信息.
     * @return UserInfoResp
     */
    @GetMapping("/getLoginUserInfo")
    @Operation(summary = "获取登录用户信息", description = "获取登录用户信息")
    public ApiResult<UserInfoVO> getLoginUserInfo() {
        return ApiResult.success(userService.getLoginUserInfo());
    }
}
