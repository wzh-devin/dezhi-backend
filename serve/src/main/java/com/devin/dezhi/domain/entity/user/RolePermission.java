package com.devin.dezhi.domain.entity.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 2025/4/25 19:24.
 *
 * <p>
 * 角色权限关联实体
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("dz_role_permission")
public class RolePermission implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色权限关联表.
     */
    @TableId("id")
    private Long id;

    /**
     * 角色id.
     */
    @TableField("role_id")
    private Long roleId;

    /**
     * 权限id.
     */
    @TableField("permission_id")
    private Long permissionId;

    /**
     * 创建时间.
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间.
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 初始化.
     */
    public void init() {
        createTime = LocalDateTime.now();
        updateTime = LocalDateTime.now();
    }
}
