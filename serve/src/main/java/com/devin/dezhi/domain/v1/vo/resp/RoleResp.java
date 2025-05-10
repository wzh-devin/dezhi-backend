package com.devin.dezhi.domain.v1.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 2025/5/10 22:07.
 *
 * <p></p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Data
@Schema(description = "角色信息响应")
public class RoleResp {

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色描述")
    private String remark;
}
