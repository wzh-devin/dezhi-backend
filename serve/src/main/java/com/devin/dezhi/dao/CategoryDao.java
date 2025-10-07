package com.devin.dezhi.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.devin.dezhi.domain.entity.Category;
import com.devin.dezhi.domain.vo.CategoryQueryVO;
import com.devin.dezhi.domain.vo.CategoryVO;
import com.devin.dezhi.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
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

    /**
     * 修改类别.
     * @param categoryVO 类别信息
     */
    public void updateCategory(final CategoryVO categoryVO) {
        LambdaUpdateWrapper<Category> updateWrapper = new LambdaUpdateWrapper<>();

        updateWrapper.eq(Category::getId, categoryVO.getId());

        if (Objects.nonNull(categoryVO.getName())) {
            updateWrapper.set(Category::getName, categoryVO.getName());
        }

        if (Objects.nonNull(categoryVO.getColor())) {
            updateWrapper.set(Category::getColor, categoryVO.getColor());
        }

        updateWrapper.set(Category::getUpdateTime, LocalDateTime.now());

        update(updateWrapper);
    }

    /**
     * 通过名称获取类别.
     * @param name 类别名称
     * @return  Category
     */
    public Category getByName(final String name) {
        return lambdaQuery()
                .eq(Category::getName, name)
                .one();
    }
}
