package com.devin.dezhi.domain.v1.entity;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 2025/6/1 22:59.
 *
 * <p>
 * 类别表
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Getter
@Setter
@ToString
@TableName("tb_category")
public class Category implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 类别id.
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 类别名称.
     */
    @TableField("name")
    private String name;

    /**
     * 类别颜色（十六位进制）.
     */
    @TableField("color")
    private String color;

    /**
     * 创建人id.
     */
    @TableField("create_user_id")
    private Long createUserId;

    /**
     * 创建时间.
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新人id.
     */
    @TableField("update_user_id")
    private Long updateUserId;

    /**
     * 更新时间.
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 删除标识（0：已删除，1：未删除）.
     */
    @TableField("del_flag")
    private Integer delFlag;

    /**
     * 初始化.
     */
    public void init() {
        long uid = Long.parseLong(StpUtil.getLoginId().toString());
        this.createUserId = uid;
        this.updateUserId = uid;
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }
}
