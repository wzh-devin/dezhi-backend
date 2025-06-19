package com.devin.dezhi.domain.v1.entity.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.devin.dezhi.domain.v1.entity.common.EntityCommon;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 2025/4/25 18:06.
 *
 * <p>
 *     用户表
 * </p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("tb_user")
@EqualsAndHashCode(callSuper = true)
public class User extends EntityCommon {

    /**
     * 用户id.
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 用户名.
     */
    @TableField(value = "username")
    private String username;

    /**
     * 邮箱.
     */
    @TableField(value = "email")
    private String email;

    /**
     * 密码.
     */
    @TableField(value = "password")
    private String password;

    /**
     * 头像.
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 创建人id.
     */
    @TableField(value = "create_user_id")
    private Long createUserId;

    /**
     * 更新人id.
     */
    @TableField(value = "update_user_id")
    private Long updateUserId;
}
