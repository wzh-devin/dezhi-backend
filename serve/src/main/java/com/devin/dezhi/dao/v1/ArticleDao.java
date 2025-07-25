package com.devin.dezhi.dao.v1;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.devin.dezhi.domain.v1.entity.Article;
import com.devin.dezhi.domain.v1.entity.ArticleTag;
import com.devin.dezhi.domain.v1.vo.ArticleQueryVO;
import com.devin.dezhi.enums.DelFlagEnum;
import com.devin.dezhi.mapper.v1.ArticleMapper;
import com.devin.dezhi.mapper.v1.ArticleTagMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 2025/7/19 22:25.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Service
public class ArticleDao extends ServiceImpl<ArticleMapper, Article> {

    private final ArticleTagMapper articleTagMapper;

    public ArticleDao(final ArticleTagMapper articleTagMapper) {
        this.articleTagMapper = articleTagMapper;
    }

    /**
     * 根据分类id查询文章.
     *
     * @param categoryIds 分类ids
     * @return 文章列表
     */
    public List<Article> getArticleByCategory(final List<Long> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            return null;
        }
        return lambdaQuery()
                .in(Article::getCategoryId, categoryIds)
                .list();
    }

    /**
     * 分页查询文章.
     *
     * @param queryVO 查询参数
     * @return 文章列表
     */
    public Page<Article> pageQuery(final ArticleQueryVO queryVO) {
        Page<Article> page = new Page<>(queryVO.getPageNum(), queryVO.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Article::getIsDeleted, DelFlagEnum.of(queryVO.getDelFlag()));

        if (Objects.nonNull(queryVO.getStatus())) {
            queryWrapper.eq(Article::getStatus, queryVO.getStatus());
        }

        if (Objects.nonNull(queryVO.getTitle())) {
            queryWrapper.like(Article::getTitle, queryVO.getTitle());
        }

        return page(page, queryWrapper);
    }

    /**
     * 根据文章id查询文章标签.
     *
     * @param articleIdSet 文章ids
     * @return 文章标签
     */
    public Map<Long, Set<Long>> getArticleTagMapByArticleIds(final Set<Long> articleIdSet) {
        return articleTagMapper.selectList(new LambdaQueryWrapper<ArticleTag>()
                        .in(ArticleTag::getArticleId, articleIdSet))
                .stream().collect(Collectors.groupingBy(
                        ArticleTag::getArticleId,
                        Collectors.mapping(ArticleTag::getTagId, Collectors.toSet())
                ));
    }
}
