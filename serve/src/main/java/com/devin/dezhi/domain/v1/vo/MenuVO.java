package com.devin.dezhi.domain.v1.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * 2025/5/22 2:03.
 *
 * <p></p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Data
@Schema(description = "菜单信息响应")
public class MenuVO {

    @Schema(description = "菜单id")
    private Long id;

    @Schema(description = "菜单key")
    private String key;

    @Schema(description = "菜单名称")
    private String name;

    @Schema(description = "菜单路径")
    private String path;

    @Schema(description = "二级菜单")
    private List<MenuVO> children;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;
}
