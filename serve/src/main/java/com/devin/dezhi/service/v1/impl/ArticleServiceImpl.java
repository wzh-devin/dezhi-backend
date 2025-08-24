package com.devin.dezhi.service.v1.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.devin.dezhi.common.utils.r.PageResult;
import com.devin.dezhi.dao.v1.ArticleDao;
import com.devin.dezhi.dao.v1.CategoryDao;
import com.devin.dezhi.dao.v1.TagDao;
import com.devin.dezhi.domain.v1.entity.Article;
import com.devin.dezhi.domain.v1.entity.ArticleTag;
import com.devin.dezhi.domain.v1.entity.Category;
import com.devin.dezhi.domain.v1.entity.Tag;
import com.devin.dezhi.domain.v1.vo.ArticleQueryVO;
import com.devin.dezhi.domain.v1.vo.ArticleSaveVO;
import com.devin.dezhi.domain.v1.vo.ArticleUpdateVO;
import com.devin.dezhi.domain.v1.vo.ArticleVO;
import com.devin.dezhi.domain.v1.vo.CategoryVO;
import com.devin.dezhi.domain.v1.vo.TagVO;
import com.devin.dezhi.enums.DelFlagEnum;
import com.devin.dezhi.enums.StatusEnum;
import com.devin.dezhi.service.generate.common.EntityGenerate;
import com.devin.dezhi.service.generate.common.RespEntityGenerate;
import com.devin.dezhi.service.v1.ArticleService;
import com.devin.dezhi.utils.AssertUtil;
import com.devin.dezhi.utils.BeanCopyUtils;
import com.devin.dezhi.utils.SnowFlake;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 2025/6/1 23:06.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleDao articleDao;

    private final SnowFlake snowFlake;

    private final TagDao tagDao;

    private final EntityGenerate entityGenerate;

    private final CategoryDao categoryDao;

    @Override
    public PageResult<ArticleVO> page(final ArticleQueryVO queryVO) {
        // 获取文章分页列表
        Page<Article> articlePage = articleDao.pageQuery(queryVO);

        List<Article> articleList = articlePage.getRecords();

        if (articleList.isEmpty()) {
            return PageResult.ofEmpty(articlePage);
        }

        // 获取文章标签
        Set<Long> articleIdSet = articleList.stream().map(Article::getId).collect(Collectors.toSet());

        // 获取文章标签映射值
        Map<Long, Set<Long>> articleTagMap = articleDao.getArticleTagMapByArticleIds(articleIdSet);
        List<Tag> tagList = tagDao.getTagListByArticleIds(articleIdSet);

        // 获取文章分类
        Map<Long, Category> categoryMap = articleDao.getCategoryMapByArticleIds(articleIdSet);

        // 组装配置文章VO
        List<ArticleVO> articleVOList = articleList.stream().map(article -> {
            Set<Long> tagIds = articleTagMap.getOrDefault(article.getId(), Collections.emptySet());
            List<TagVO> tagVOList = new ArrayList<>();
            if (!tagIds.isEmpty()) {
                tagVOList = tagList.stream().filter(tag -> tagIds.contains(tag.getId()))
                        .map(tag -> BeanCopyUtils.copy(tag, TagVO.class))
                        .toList();
            }
            // 设置标签列表VO
            ArticleVO articleVO = RespEntityGenerate.generateArticleVO(article);
            articleVO.setTagList(tagVOList);
            // 设置分类VO
            Category category = categoryMap.get(article.getId());
            if (Objects.nonNull(category)) {
                articleVO.setCategory(BeanCopyUtils.copy(category, CategoryVO.class));
            }
            return articleVO;
        }).toList();
        return PageResult.of(articleVOList, articlePage);
    }

    @Override
    public void saveArticle(final ArticleSaveVO articleSaveVO) {
        Article article = BeanCopyUtils.copy(articleSaveVO, Article.class);
        if (Objects.isNull(article.getId())) {
            article.setId(snowFlake.nextId());
            article.init();
        } else {
            article.setUpdateTime(LocalDateTime.now());
        }
        article.setIsDeleted(DelFlagEnum.NORMAL.getFlag());

        // 保存文章标签关联
        if (!articleSaveVO.getTagIdList().isEmpty()) {
            List<ArticleTag> articleTagList = articleSaveVO.getTagIdList().stream().map(tagId ->
                    entityGenerate.generateArticleTag(article.getId(), tagId)).toList();

            articleDao.saveArticleTagBatch(articleTagList);
        }

        articleDao.save(article);
    }

    @Override
    public void delArticle(final List<Long> idList) {
        articleDao.delBatchLogicByIds(idList);
    }

    @Override
    public void recoverArticle(final List<Long> idList) {
        articleDao.recoverBatchLogicByIds(idList);
    }

    @Override
    public void cleanRecycle() {
        // 获取需要删除的文章id列表
        List<Article> articleList = articleDao.getByDelFlag(DelFlagEnum.IS_DELETED.getFlag());
        if (articleList.isEmpty()) {
            return;
        }
        List<Long> idList = articleList.stream().map(Article::getId).toList();

        // 删除文章
        articleDao.delBatchByIds(idList);

        // 删除文章相关标签
        articleDao.delArticleTagBatch(idList);
    }

    @Override
    public void editArticle(final ArticleUpdateVO articleUpdateVO) {
        ArticleSaveVO articleSaveVO = BeanCopyUtils.copy(articleUpdateVO, ArticleSaveVO.class);

        // 删除文章
        articleDao.removeById(articleUpdateVO.getId());

        // 删除文章相关标签
        articleDao.delArticleTagBatch(Collections.singletonList(articleUpdateVO.getId()));

        // 新增文章信息
        saveArticle(articleSaveVO);
    }

    @Override
    public ArticleVO getArticleInfo(final Long articleId) {
        // 查询文章信息
        Article article = articleDao.getById(articleId);
        AssertUtil.isNotEmpty(article, "文章不存在");

        ArticleVO articleVO = BeanCopyUtils.copy(article, ArticleVO.class);

        // 获取文章标签
        Set<Long> articleTagIdList = articleDao.getArticleTagByArticleId(article.getId());
        if (!articleTagIdList.isEmpty()) {
            List<Tag> tagList = tagDao.getTagListByIds(articleTagIdList);
            List<TagVO> tagVOList = BeanCopyUtils.copy(tagList, TagVO.class);
            articleVO.setTagList(tagVOList);
        }

        // 获取文章分类
        if (Objects.nonNull(article.getCategoryId())) {
            Category category = categoryDao.getById(article.getCategoryId());
            CategoryVO categoryVO = BeanCopyUtils.copy(category, CategoryVO.class);
            articleVO.setCategory(categoryVO);
        }

        return articleVO;
    }

    @Override
    public ArticleVO saveArticleInit() {
        Article article = new Article();
        article.setId(snowFlake.nextId());
        article.setTitle("新建标题");
        article.init();
        articleDao.save(article);
        return BeanCopyUtils.copy(article, ArticleVO.class);
    }

    @Override
    public void updateStatus(final Long id, final StatusEnum status) {
        articleDao.updateStatusById(id, status);
    }
}
