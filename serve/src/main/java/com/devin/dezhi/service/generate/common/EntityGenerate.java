package com.devin.dezhi.service.generate.common;

import com.devin.dezhi.domain.v1.entity.ArticleTag;
import com.devin.dezhi.domain.v1.entity.Material;
import com.devin.dezhi.enums.DelFlagEnum;
import com.devin.dezhi.model.FileInfo;
import com.devin.dezhi.utils.SnowFlake;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 2025/6/2 1:28.
 *
 * <p>
 * 实体类构建
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class EntityGenerate {

    private final SnowFlake snowFlake;

    /**
     * 生成素材实体类.
     *
     * @param fileInfo    文件信息
     * @param storageType 存储类型
     * @param url         文件地址
     * @return Material
     */
    public Material generateMaterial(final FileInfo fileInfo, final String storageType, final String url) {
        Material material = new Material();
        material.setId(snowFlake.nextId());
        material.setName(fileInfo.getOriginalFileName());
        material.setMd5(fileInfo.getMd5());
        material.setUrl(url);
        material.setSize(fileInfo.getSize());
        material.setIsDeleted(DelFlagEnum.NORMAL.getFlag());
        material.setFileType(fileInfo.getSuffix().toUpperCase());
        material.setStorageType(storageType);
        material.init();
        return material;
    }

    /**
     * 生成文章标签实体类.
     * @param articleId 文章id
     * @param tagId 标签id
     * @return ArticleTag
     */
    public ArticleTag generateArticleTag(final Long articleId, final Long tagId) {
        ArticleTag articleTag = new ArticleTag();
        articleTag.setId(snowFlake.nextId());
        articleTag.setArticleId(articleId);
        articleTag.setTagId(tagId);
        articleTag.init();
        return articleTag;
    }
}
