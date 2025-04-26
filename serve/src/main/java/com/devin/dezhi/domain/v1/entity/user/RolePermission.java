package com.devin.dezhi.domain.v1.entity.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.devin.dezhi.domain.v1.entity.common.EntityCommon;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 2025/4/25 19:24.
 *
 * <p>
 *     角色权限关联实体
 * </p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("tb_role_permission")
@EqualsAndHashCode(callSuper = true)
public class RolePermission extends EntityCommon {

    /**
     * 角色权限关联id.
     */
    @TableId
    private Long id;

    /**
     * 角色id.
     */
    @TableField(value = "role_id")
    private Long roleId;

    /**
     * 权限id.
     */
    @TableField(value = "permission_id")
    private Long permissionId;
}
