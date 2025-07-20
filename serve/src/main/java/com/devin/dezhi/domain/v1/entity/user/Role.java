package com.devin.dezhi.domain.v1.entity.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

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
public class Role implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色id.
     */
    @TableId("id")
    private Long id;

    /**
     * 角色名称.
     */
    @TableField("role")
    private String role;

    /**
     * 角色描述.
     */
    @TableField("remark")
    private String remark;

    /**
     * 创建时间.
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 修改时间.
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 初始化.
     */
    public void init() {
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }
}
