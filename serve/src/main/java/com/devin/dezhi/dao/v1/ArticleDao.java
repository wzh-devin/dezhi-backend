package com.devin.dezhi.dao.v1;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.devin.dezhi.domain.v1.entity.Article;
import com.devin.dezhi.mapper.v1.ArticleMapper;
import org.springframework.stereotype.Service;
import java.util.List;

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

    /**
     * 根据分类id查询文章.
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
}
