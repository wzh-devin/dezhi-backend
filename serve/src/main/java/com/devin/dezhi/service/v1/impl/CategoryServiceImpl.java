package com.devin.dezhi.service.v1.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.devin.dezhi.constant.ErrMsgConstant;
import com.devin.dezhi.dao.v1.ArticleDao;
import com.devin.dezhi.dao.v1.CategoryDao;
import com.devin.dezhi.domain.v1.entity.Article;
import com.devin.dezhi.domain.v1.entity.Category;
import com.devin.dezhi.domain.v1.vo.CategoryQueryVO;
import com.devin.dezhi.domain.v1.vo.CategoryVO;
import com.devin.dezhi.service.v1.CategoryService;
import com.devin.dezhi.utils.AssertUtil;
import com.devin.dezhi.utils.BeanCopyUtils;
import com.devin.dezhi.utils.SnowFlake;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final SnowFlake snowFlake;

    private final CategoryDao categoryDao;

    private final ArticleDao articleDao;

    @Override
    public void saveCategory(final CategoryVO categoryVO) {
        // 检查类别名称是否重复
        Category checkNameDuplicate = categoryDao.getByName(categoryVO.getName());
        AssertUtil.isEmpty(checkNameDuplicate, ErrMsgConstant.CATEGORY_NAME_DUPLICATE);

        // 新增类别
        Category category = BeanCopyUtils.copy(categoryVO, Category.class);
        category.setId(snowFlake.nextId());
        category.init();
        categoryDao.save(category);
    }

    @Override
    public void delCategories(final List<Long> ids) {
        List<Article> articleList = articleDao.getArticleByCategory(ids);
        AssertUtil.isEmpty(articleList, ErrMsgConstant.CATEGORY_HAS_QUOTE);
        categoryDao.removeBatchByIds(ids);
    }

    @Override
    public Page<Category> page(final CategoryQueryVO queryVO) {
        return categoryDao.getPageList(queryVO);
    }

    @Override
    public void editCategory(final CategoryVO categoryVO) {
        categoryDao.updateCategory(categoryVO);
    }

    @Override
    public List<CategoryVO> getCategoryOptional() {
        return categoryDao.list().stream()
                .map(category -> BeanCopyUtils.copy(category, CategoryVO.class))
                .toList();
    }
}
