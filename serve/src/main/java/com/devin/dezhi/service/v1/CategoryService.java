package com.devin.dezhi.service.v1;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.devin.dezhi.domain.v1.entity.Category;
import com.devin.dezhi.domain.v1.vo.CategoryQueryVO;
import com.devin.dezhi.domain.v1.vo.CategoryVO;
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
public interface CategoryService {

    /**
     * 保存类别.
     * @param categoryVO 类别信息
     */
    void saveCategory(CategoryVO categoryVO);

    /**
     * 删除类别.
     * @param ids 类别id列表
     */
    void delCategories(List<Long> ids);

    /**
     * 分页查询类别.
     * @param queryVO 查询参数
     * @return 类别列表
     */
    Page<Category> page(CategoryQueryVO queryVO);

    /**
     * 编辑类别.
     * @param categoryVO 类别信息
     */
    void editCategory(CategoryVO categoryVO);
}
