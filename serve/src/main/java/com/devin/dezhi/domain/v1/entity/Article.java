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
 * 文章素材关联实体
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Getter
@Setter
@ToString
@TableName("tb_article")
public class Article implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文章id.
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 文章标题.
     */
    @TableField("title")
    private String title;

    /**
     * 文章内容.
     */
    @TableField("content")
    private String content;

    /**
     * 文章状态（0：草稿，1：已发布）.
     */
    @TableField("state")
    private Integer state;

    /**
     * 创建的用户id.
     */
    @TableField("create_user_id")
    private Long createUserId;

    /**
     * 创建时间.
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新用户id.
     */
    @TableField("update_user_id")
    private Long updateUserId;

    /**
     * 更新时间.
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 删除标识（0：未删除，1：已删除）.
     */
    @TableField("del_flag")
    private Integer delFlag;

    /**
     * 热门文章（0：普通文章，1：推荐文章，2：热门文章）.
     */
    @TableField("hot_flag")
    private Integer hotFlag;
}
