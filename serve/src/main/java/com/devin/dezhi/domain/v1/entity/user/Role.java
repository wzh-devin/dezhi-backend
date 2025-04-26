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
 *     角色表
 * </p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("tb_role")
@EqualsAndHashCode(callSuper = true)
public class Role extends EntityCommon {

    /**
     * 角色id.
     */
    @TableId
    private Long id;

    /**
     * 角色名.
     */
    @TableField(value = "role")
    private String role;

    /**
     * 角色描述.
     */
    @TableField(value = "remark")
    private String remark;
}
