package com.devin.dezhi.service.v1.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.devin.dezhi.common.utils.r.PageResult;
import com.devin.dezhi.dao.v1.ArticleDao;
import com.devin.dezhi.dao.v1.TagDao;
import com.devin.dezhi.domain.v1.entity.Article;
import com.devin.dezhi.domain.v1.entity.Tag;
import com.devin.dezhi.domain.v1.vo.ArticleQueryVO;
import com.devin.dezhi.domain.v1.vo.ArticleSaveVO;
import com.devin.dezhi.domain.v1.vo.ArticleVO;
import com.devin.dezhi.domain.v1.vo.TagVO;
import com.devin.dezhi.enums.DelFlagEnum;
import com.devin.dezhi.service.generate.common.RespEntityGenerate;
import com.devin.dezhi.service.v1.ArticleService;
import com.devin.dezhi.utils.BeanCopyUtils;
import com.devin.dezhi.utils.SnowFlake;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
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
        List<Tag> tagList = tagDao.getTagListByIds(articleIdSet);

        // 组装配置文章VO
        List<ArticleVO> articleVOList = articleList.stream().map(article -> {
            Set<Long> tagIds = articleTagMap.get(article.getId());
            List<TagVO> tagVOList = tagList.stream().filter(tag -> tagIds.contains(tag.getId()))
                    .map(tag -> BeanCopyUtils.copy(tag, TagVO.class))
                    .toList();
            ArticleVO articleVO = RespEntityGenerate.generateArticleVO(article);
            articleVO.setTagList(tagVOList);
            return articleVO;
        }).toList();
        return PageResult.of(articleVOList, articlePage);
    }

    @Override
    public void saveArticle(final ArticleSaveVO articleSaveVO) {
        Article article = BeanCopyUtils.copy(articleSaveVO, Article.class);
        article.setId(snowFlake.nextId());
        article.init();
        article.setIsDeleted(DelFlagEnum.NORMAL.getFlag());
        articleDao.save(article);
    }
}
