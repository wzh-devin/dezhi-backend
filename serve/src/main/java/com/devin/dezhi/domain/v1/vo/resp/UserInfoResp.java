package com.devin.dezhi.domain.v1.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * 2025/5/10 21:53.
 *
 * <p></p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Data
@Schema(description = "用户信息响应")
public class UserInfoResp {

    @Schema(description = "用户id")
    private Long uid;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "用户邮箱")
    private String email;

    @Schema(description = "用户角色信息")
    private List<RoleResp> roles;

    @Schema(description = "用户权限信息")
    private List<PermissionResp> permissions;

    @Schema(description = "创建用户")
    private String createUser;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新用户")
    private String updateUser;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "删除标志")
    private Integer delFlag;

}
