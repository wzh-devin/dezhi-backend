package com.devin.dezhi.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.devin.dezhi.domain.entity.Article;
import com.devin.dezhi.domain.entity.ArticleTag;
import com.devin.dezhi.domain.entity.Category;
import com.devin.dezhi.domain.vo.ArticleQueryVO;
import com.devin.dezhi.enums.DelFlagEnum;
import com.devin.dezhi.enums.StatusEnum;
import com.devin.dezhi.mapper.ArticleMapper;
import com.devin.dezhi.mapper.ArticleTagMapper;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.HashMap;
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

    private final CategoryDao categoryDao;

    public ArticleDao(final ArticleTagMapper articleTagMapper, final CategoryDao categoryDao) {
        this.articleTagMapper = articleTagMapper;
        this.categoryDao = categoryDao;
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
            queryWrapper.eq(Article::getStatus, queryVO.getStatus().getStatus());
        }

        if (Objects.nonNull(queryVO.getTitle())) {
            queryWrapper.like(Article::getTitle, queryVO.getTitle());
        }

        queryWrapper.orderByDesc(Article::getUpdateTime);

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

    /**
     * 批量保存文章标签.
     *
     * @param articleTagList 文章标签列表
     */
    public void saveArticleTagBatch(final Collection<ArticleTag> articleTagList) {
        articleTagMapper.insertBatchSomeColumn(articleTagList);
    }

    /**
     * 批量逻辑删除文章.
     *
     * @param idList 文章id列表
     */
    public void delBatchLogicByIds(final Collection<Long> idList) {
        lambdaUpdate()
                .in(Article::getId, idList)
                .set(Article::getStatus, StatusEnum.DRAFT.getStatus())
                .set(Article::getIsDeleted, DelFlagEnum.IS_DELETED.getFlag())
                .update();
    }

    /**
     * 批量逻辑恢复文章.
     *
     * @param idList 文章id列表
     */
    public void recoverBatchLogicByIds(final Collection<Long> idList) {
        lambdaUpdate()
                .in(Article::getId, idList)
                .set(Article::getIsDeleted, DelFlagEnum.NORMAL.getFlag())
                .update();
    }

    /**
     * 批量物理删除文章.
     *
     * @param idList 文章标签id列表
     */
    public void delBatchByIds(final Collection<Long> idList) {
        lambdaUpdate()
                .eq(Article::getIsDeleted, DelFlagEnum.IS_DELETED.getFlag())
                .in(Article::getId, idList)
                .remove();
    }

    /**
     * 批量删除文章标签.
     *
     * @param idList 文章标签id列表
     */
    public void delArticleTagBatch(final List<Long> idList) {
        List<Long> articleTagIdList = articleTagMapper.selectList(new LambdaQueryWrapper<ArticleTag>()
                        .in(ArticleTag::getArticleId, idList))
                .stream().map(ArticleTag::getId).toList();
        if (!articleTagIdList.isEmpty()) {
            articleTagMapper.deleteBatchIds(articleTagIdList);
        }
    }

    /**
     * 根据文章id查询文章标签.
     *
     * @param id 文章id
     * @return 文章标签
     */
    public Set<Long> getArticleTagByArticleId(final Long id) {
        return articleTagMapper.selectList(new LambdaQueryWrapper<ArticleTag>()
                        .in(ArticleTag::getArticleId, id))
                .stream().map(ArticleTag::getTagId)
                .collect(Collectors.toSet());
    }

    /**
     * 根据文章id查询文章分类.
     *
     * @param articleIdSet 文章id列表
     * @return 文章分类
     */
    public Map<Long, Category> getCategoryMapByArticleIds(final Set<Long> articleIdSet) {
        Map<Long, Category> categoryMap = new HashMap<>();
        List<Article> articleList = lambdaQuery()
                .in(Article::getId, articleIdSet)
                .list();
        Set<Long> categoryIdSet = articleList.stream().map(Article::getCategoryId).collect(Collectors.toSet());
        List<Category> categoryList = categoryDao.listByIds(categoryIdSet);
        for (Article article : articleList) {
            categoryList.stream()
                    .filter(c -> c.getId().equals(article.getCategoryId()))
                    .findFirst().ifPresent(category -> {
                        categoryMap.put(article.getId(), category);
                    });
        }
        return categoryMap;
    }

    /**
     * 根据文章状态查询文章.
     * @param flag 状态
     * @return 文章列表
     */
    public List<Article> getByDelFlag(final Integer flag) {
        return lambdaQuery()
                .eq(Article::getIsDeleted, flag)
                .list();
    }

    /**
     * 修改文章状态.
     * @param id 文章id
     * @param status 状态
     */
    public void updateStatusById(final Long id, final StatusEnum status) {
        lambdaUpdate()
                .eq(Article::getId, id)
                .set(Article::getStatus, status.getStatus())
                .update();
    }
}
