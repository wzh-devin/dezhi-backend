package com.devin.dezhi.service;

import com.devin.dezhi.utils.r.PageResult;
import com.devin.dezhi.domain.vo.ArticleQueryVO;
import com.devin.dezhi.domain.vo.ArticleSaveVO;
import com.devin.dezhi.domain.vo.ArticleUpdateVO;
import com.devin.dezhi.domain.vo.ArticleVO;
import com.devin.dezhi.enums.StatusEnum;
import java.util.List;

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
     *
     * @param queryVO 查询参数
     * @return 文章列表
     */
    PageResult<ArticleVO> page(ArticleQueryVO queryVO);

    /**
     * 保存文章.
     *
     * @param articleSaveVO 文章保存参数
     */
    void saveArticle(ArticleSaveVO articleSaveVO);

    /**
     * 批量删除文章.
     *
     * @param idList 文章id列表
     */
    void delArticle(List<Long> idList);

    /**
     * 批量恢复文章.
     *
     * @param idList 文章id列表
     */
    void recoverArticle(List<Long> idList);

    /**
     * 清空回收站.
     *
     */
    void cleanRecycle();

    /**
     * 修改文章.
     *
     * @param articleUpdateVO 文章修改参数
     */
    void editArticle(ArticleUpdateVO articleUpdateVO);

    /**
     * 获取文章信息.
     *
     * @param articleId 文章id
     * @return 文章信息
     */
    ArticleVO getArticleInfo(Long articleId);

    /**
     * 初始化新增文章.
     *
     * @return 新增文章信息
     */
    ArticleVO saveArticleInit();

    /**
     * 修改文章状态.
     * @param id 文章id
     * @param status 状态
     */
    void updateStatus(Long id, StatusEnum status);
}
