package com.devin.dezhi.domain.v1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 2025/4/26 15:10.
 *
 * <p>
 *     用户信息请求
 * </p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Data
@Schema(description = "用户信息请求实体")
public class UserInfoDTO {

    @Max(16)
    @Schema(description = "用户名")
    private String username;

    @Email
    @Schema(description = "邮箱")
    private String email;

    @Size(max = 6)
    @Schema(description = "验证码")
    private Integer code;

    @Min(6)
    @Schema(description = "密码")
    private String password;
}
