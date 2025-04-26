package com.devin.dezhi.domain.v1.entity.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.devin.dezhi.domain.v1.entity.common.EntityCommon;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 2025/4/25 19:20.
 *
 * <p>
 *     权限表
 * </p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("tb_permission")
@EqualsAndHashCode(callSuper = true)
public class Permission extends EntityCommon {

    /**
     * 权限id.
     */
    @TableId
    private Long id;

    /**
     * 权限名称.
     */
    @TableField(value = "permission")
    private String permission;

    /**
     * 权限描述.
     */
    @TableField(value = "remark")
    private String remark;
}
