package com.devin.dezhi.service.v1;

import com.devin.dezhi.common.utils.r.PageResult;
import com.devin.dezhi.domain.v1.vo.ArticleQueryVO;
import com.devin.dezhi.domain.v1.vo.ArticleSaveVO;
import com.devin.dezhi.domain.v1.vo.ArticleVO;

/**
 * 2025/6/1 23:06.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
public interface ArticleService {

    /**
     * 分页查询文章.
     * @param queryVO 查询参数
     * @return 文章列表
     */
    PageResult<ArticleVO> page(ArticleQueryVO queryVO);

    /**
     * 保存文章.
     * @param articleSaveVO 文章保存参数
     */
    void saveArticle(ArticleSaveVO articleSaveVO);
}
