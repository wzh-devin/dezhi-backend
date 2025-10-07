package com.devin.dezhi.controller;

import com.devin.dezhi.annocation.ApiV1;
import com.devin.dezhi.utils.r.ApiResult;
import com.devin.dezhi.domain.vo.user.UserInfoQueryVO;
import com.devin.dezhi.domain.vo.user.LoginVO;
import com.devin.dezhi.domain.vo.user.UserInfoVO;
import com.devin.dezhi.service.UserService;
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
@Tag(name = "user")
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 账号登录.
     * @param userInfoQueryVO 用户登录信息
     * @return LoginResp
     */
    @PostMapping("/loginAccount")
    @Operation(summary = "账密登录", description = "账密登录")
    public ApiResult<LoginVO> loginAccount(@RequestBody final UserInfoQueryVO userInfoQueryVO) {
        return ApiResult.success(userService.loginAccount(userInfoQueryVO));
    }

    /**
     * 用户登出.
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
     * @return Void
     */
    @DeleteMapping("/deregisterAccount")
    @Operation(summary = "用户注销", description = "用户注销")
    public ApiResult<Void> deregisterAccount() {
        userService.deregisterAccount();
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
    public ApiResult<Void> getEmailCode(@RequestParam(name = "email", required = true) final String email) {
        userService.getEmailCode(email);
        return ApiResult.success();
    }

    /**
     * 邮箱验证码登录.
     * @param userInfoQueryVO 用户登录信息
     * @return LoginResp
     */
    @PostMapping("/loginEmail")
    @Operation(summary = "邮箱验证码登录", description = "邮箱验证码登录")
    public ApiResult<LoginVO> loginEmail(@RequestBody final UserInfoQueryVO userInfoQueryVO) {
        return ApiResult.success(userService.loginEmail(userInfoQueryVO));
    }

    /**
     * 用户注册.
     * @param userInfoQueryVO 用户信息
     * @return Void
     */
    @PutMapping("/signup")
    @Operation(summary = "用户注册", description = "用户注册")
    public ApiResult<Void> signup(@RequestBody final UserInfoQueryVO userInfoQueryVO) {
        userService.signup(userInfoQueryVO);
        return ApiResult.success();
    }

    /**
     * 忘记密码.
     * @param userInfoQueryVO 用户信息
     * @return Void
     */
    @PostMapping("/forgetPassword")
    @Operation(summary = "忘记密码", description = "忘记密码")
    public ApiResult<Void> forgetPassword(@RequestBody final UserInfoQueryVO userInfoQueryVO) {
        userService.forgetPassword(userInfoQueryVO);
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
