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
 *     用户角色关联表
 * </p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("tb_user_role")
@EqualsAndHashCode(callSuper = true)
public class UserRole extends EntityCommon {

    /**
     * 用户角色关联id.
     */
    @TableId
    private Long id;

    /**
     * 用户id.
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 角色id.
     */
    @TableField(value = "role_id")
    private Long roleId;
}
