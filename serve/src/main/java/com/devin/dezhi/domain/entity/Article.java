package com.devin.dezhi.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
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
@TableName("dz_article")
public class Article implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文章id.
     */
    @TableId("id")
    private Long id;

    /**
     * 文章分类.
     */
    @TableField("category_id")
    private Long categoryId;

    /**
     * 文章标题.
     */
    @TableField("title")
    private String title;

    /**
     * 文章简介.
     */
    @TableField("summary")
    private String summary;

    /**
     * 文章内容.
     */
    @TableField("content")
    private String content;

    /**
     * 文章内容（markdown格式）.
     */
    @TableField("content_md")
    private String contentMd;

    /**
     * 文章url地址.
     */
    @TableField("url")
    private String url;

    /**
     * 文章是否置顶（0：否；1：是）.
     */
    @TableField("is_stick")
    private Integer isStick;

    /**
     * 文章状态（0：草稿；1：发布）.
     */
    @TableField("status")
    private Integer status;

    /**
     * 是否热门文章（0：否；1：是）.
     */
    @TableField("is_hot")
    private Integer isHot;

    /**
     * 是否ai创建（0：否；1：是）.
     */
    @TableField("is_ai")
    private Integer isAi;

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
     * 文章摘要向量.
     */
    @TableField(value = "summary_embedding", insertStrategy = FieldStrategy.ALWAYS, updateStrategy = FieldStrategy.ALWAYS)
    private String summaryEmbedding;

    /**
     * 初始化.
     */
    public void init() {
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }
}
