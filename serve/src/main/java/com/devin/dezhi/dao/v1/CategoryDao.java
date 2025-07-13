package com.devin.dezhi.dao.v1;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.devin.dezhi.domain.v1.entity.ArticleCategory;
import com.devin.dezhi.domain.v1.entity.Category;
import com.devin.dezhi.domain.v1.vo.CategoryQueryVO;
import com.devin.dezhi.mapper.v1.ArticleCategoryMapper;
import com.devin.dezhi.mapper.v1.CategoryMapper;
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
public class CategoryDao extends ServiceImpl<CategoryMapper, Category> {

    private final ArticleCategoryMapper articleCategoryMapper;

    /**
     * 获取文章类别.
     *
     * @param ids 类别ids
     * @return List
     */
    public List<ArticleCategory> getArticleCategoryByIds(final List<Long> ids) {
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        return articleCategoryMapper.selectList(new LambdaQueryWrapper<ArticleCategory>()
                .in(ArticleCategory::getCategoryId, ids));
    }

    /**
     * 获取类别列表.
     *
     * @param queryVO 查询参数
     * @return Page
     */
    public Page<Category> getPageList(final CategoryQueryVO queryVO) {
        Page<Category> page = new Page<>(queryVO.getPageNum(), queryVO.getPageSize());

        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();

        if (Objects.nonNull(queryVO.getName())) {
            queryWrapper.eq(Category::getName, queryVO.getName());
        }

        return page(page, queryWrapper);
    }
}
