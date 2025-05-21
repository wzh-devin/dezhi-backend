package com.devin.dezhi.dao.v1;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.devin.dezhi.domain.v1.entity.Menu;
import com.devin.dezhi.enums.FlagEnum;
import com.devin.dezhi.mapper.v1.MenuMapper;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 2025/5/22 2:00.
 *
 * <p></p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Service
public class MenuDao extends ServiceImpl<MenuMapper, Menu> {

    /**
     * 获取所有的父级顶级菜单列表.
     * @return List
     */
    public List<Menu> getParentMenuList() {
        return lambdaQuery()
                .eq(Menu::getDelFlag, FlagEnum.NORMAL.getFlag())
                .eq(Menu::getParentId, 0)
                .list();
    }

    /**
     * 获取所有的子级菜单列表.
     * @return List
     */
    public List<Menu> getSubMenuList() {
        return lambdaQuery()
                .eq(Menu::getDelFlag, FlagEnum.NORMAL.getFlag())
                .ne(Menu::getParentId, 0)
                .list();
    }
}
