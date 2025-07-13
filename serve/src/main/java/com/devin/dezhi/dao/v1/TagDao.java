package com.devin.dezhi.dao.v1;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.devin.dezhi.domain.v1.entity.ArticleTag;
import com.devin.dezhi.domain.v1.entity.Tag;
import com.devin.dezhi.domain.v1.vo.TagQueryVO;
import com.devin.dezhi.mapper.v1.ArticleTagMapper;
import com.devin.dezhi.mapper.v1.TagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
}
