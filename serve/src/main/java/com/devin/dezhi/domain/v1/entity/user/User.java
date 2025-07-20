package com.devin.dezhi.domain.v1.entity.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

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
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户id.
     */
    @TableId("id")
    private Long id;

    /**
     * 用户名称.
     */
    @TableField("username")
    private String username;

    /**
     * 用户密码.
     */
    @TableField("password")
    private String password;

    /**
     * 用户邮箱.
     */
    @TableField("email")
    private String email;

    /**
     * 用户头像.
     */
    @TableField("avatar")
    private String avatar;

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
     * 是否被删除（0：未删除；1：已删除）.
     */
    @TableField("is_deleted")
    private Integer isDeleted;

    /**
     * 初始化.
     */
    public void init() {
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }
}
