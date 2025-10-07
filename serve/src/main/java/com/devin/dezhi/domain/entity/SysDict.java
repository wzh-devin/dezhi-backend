package com.devin.dezhi.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 2025/6/1 23:02.
 *
 * <p>
 * 系统字典表
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("sys_dict")
public class SysDict implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 系统字典id.
     */
    @TableId("id")
    private Long id;

    /**
     * 字典code.
     */
    @TableField("code")
    private String code;

    /**
     * code类型.
     */
    @TableField("type")
    private String type;

    /**
     * code描述.
     */
    @TableField("name")
    private String name;

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
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }
}
