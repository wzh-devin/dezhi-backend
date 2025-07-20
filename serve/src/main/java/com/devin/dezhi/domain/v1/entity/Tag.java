package com.devin.dezhi.domain.v1.entity;

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
 * 标签表
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Getter
@Setter
@ToString
@TableName("tb_tag")
public class Tag implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 标签id.
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 标签名称.
     */
    @TableField("name")
    private String name;

    /**
     * 标签颜色（十六进制类型）.
     */
    @TableField("color")
    private String color;

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
