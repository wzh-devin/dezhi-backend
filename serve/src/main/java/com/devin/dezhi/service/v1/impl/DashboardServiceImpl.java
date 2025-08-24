package com.devin.dezhi.service.v1.impl;

import com.devin.dezhi.common.utils.r.PageResult;
import com.devin.dezhi.dao.v1.ArticleDao;
import com.devin.dezhi.domain.v1.entity.Article;
import com.devin.dezhi.domain.v1.vo.ArticleQueryVO;
import com.devin.dezhi.domain.v1.vo.ArticleVO;
import com.devin.dezhi.domain.v1.vo.DashboardVO;
import com.devin.dezhi.enums.DelFlagEnum;
import com.devin.dezhi.enums.StatusEnum;
import com.devin.dezhi.service.v1.ArticleService;
import com.devin.dezhi.service.v1.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 2025/8/24 20:52.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final ArticleDao articleDao;

    private final ArticleService articleService;

    @Override
    public DashboardVO getDashboardInfo() {
        // 获取文章总数
        Integer articleCount = articleDao.lambdaQuery()
                .eq(Article::getIsDeleted, DelFlagEnum.NORMAL.getFlag())
                .count().intValue();
        // 获取发布文章总数
        Integer articleIsPublishedCount = articleDao.lambdaQuery()
                .eq(Article::getIsDeleted, DelFlagEnum.NORMAL.getFlag())
                .eq(Article::getStatus, StatusEnum.IS_PUBLISH.getStatus())
                .count().intValue();
        // 获取修改文章列表前五
        ArticleQueryVO articleQueryVO = new ArticleQueryVO();
        articleQueryVO.setPageNum(0);
        articleQueryVO.setPageSize(5);
        articleQueryVO.setDelFlag(DelFlagEnum.NORMAL);
        PageResult<ArticleVO> page = articleService.page(articleQueryVO);
        List<ArticleVO> articleVOList = page.getRecords();
        // 构建仪表盘数据
        DashboardVO dashboardVO = new DashboardVO();
        dashboardVO.setArticleCount(articleCount);
        dashboardVO.setPublishArticleCount(articleIsPublishedCount);
        dashboardVO.setArticleTop5List(articleVOList);
        return dashboardVO;
    }
}
