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
 * 文章详情表
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Getter
@Setter
@ToString
@TableName("tb_article_detail")
public class ArticleDetail implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文章详情表id.
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 文章访问地址.
     */
    @TableField("url")
    private String url;

    /**
     * 文章id.
     */
    @TableField("article_id")
    private Long articleId;

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
}
