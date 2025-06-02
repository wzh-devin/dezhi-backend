package com.devin.dezhi.domain.v1.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.devin.dezhi.domain.v1.entity.common.EntityCommon;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@Data
@TableName("tb_article_material")
@EqualsAndHashCode(callSuper = true)
public class ArticleMaterial extends EntityCommon {

    @TableId(value = "id")
    private Long id;

    @TableField(value = "article_id")
    private Long articleId;

    @TableField(value = "material_id")
    private Long materialId;
}
