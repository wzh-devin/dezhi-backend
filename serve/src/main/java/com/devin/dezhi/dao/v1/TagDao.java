package com.devin.dezhi.dao.v1;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.devin.dezhi.domain.v1.entity.ArticleTag;
import com.devin.dezhi.domain.v1.entity.Tag;
import com.devin.dezhi.domain.v1.vo.TagQueryVO;
import com.devin.dezhi.domain.v1.vo.TagVO;
import com.devin.dezhi.mapper.v1.ArticleTagMapper;
import com.devin.dezhi.mapper.v1.TagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 2025/7/13 22:05.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class TagDao extends ServiceImpl<TagMapper, Tag> {

    private final ArticleTagMapper articleTagMapper;

    /**
     * 获取文章标签.
     *
     * @param ids 标签ids
     * @return List
     */
    public List<ArticleTag> getArticleTagByIds(final List<Long> ids) {
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        return articleTagMapper.selectList(new LambdaQueryWrapper<ArticleTag>()
                .in(ArticleTag::getTagId, ids));
    }

    /**
     * 获取标签列表.
     *
     * @param queryVO 查询参数
     * @return Page
     */
    public Page<Tag> getPageList(final TagQueryVO queryVO) {
        Page<Tag> page = new Page<>(queryVO.getPageNum(), queryVO.getPageSize());

        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();

        if (Objects.nonNull(queryVO.getName())) {
            queryWrapper.eq(Tag::getName, queryVO.getName());
        }

        return page(page, queryWrapper);
    }

    /**
     * 修改标签.
     *
     * @param tagVO 标签
     */
    public void updateTag(final TagVO tagVO) {
        LambdaUpdateWrapper<Tag> updateWrapper = new LambdaUpdateWrapper<>();

        updateWrapper.eq(Tag::getId, tagVO.getId());

        if (Objects.nonNull(tagVO.getName())) {
            updateWrapper.set(Tag::getName, tagVO.getName());
        }

        if (Objects.nonNull(tagVO.getColor())) {
            updateWrapper.set(Tag::getColor, tagVO.getColor());
        }
        updateWrapper.set(Tag::getUpdateTime, LocalDateTime.now());

        update(updateWrapper);
    }

    /**
     * 根据标签名获取标签.
     *
     * @param name 标签名
     * @return Tag
     */
    public Tag getTagByName(final String name) {
        return lambdaQuery()
                .eq(Tag::getName, name)
                .one();
    }

    /**
     * 根据标签ids获取标签列表.
     *
     * @param tagIds 标签ids
     * @return List
     */
    public List<Tag> getTagListByIds(final Set<Long> tagIds) {
        return lambdaQuery()
                .in(Tag::getId, tagIds)
                .list();
    }
}
