package com.devin.dezhi.service.v1.impl;

import com.devin.dezhi.dao.v1.MenuDao;
import com.devin.dezhi.domain.v1.entity.Menu;
import com.devin.dezhi.domain.v1.vo.MenuVO;
import com.devin.dezhi.service.v1.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 2025/5/22 1:37.
 *
 * <p></p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuDao menuDao;

    @Override
    public List<MenuVO> getMenuData() {
        // 查询顶级父级菜单
        List<Menu> parentMenuList = menuDao.getParentMenuList();

        // 查询所有的二级菜单
        List<Menu> subMenuList = menuDao.getSubMenuList();

        // 预分组映射
        Map<Long, List<Menu>> subMenuMap = subMenuList.stream()
                .collect(Collectors.groupingBy(Menu::getParentId));

        // 组装最终数据
        return parentMenuList.stream().map(parentMenu -> {
            MenuVO menuVO = new MenuVO();
            BeanUtils.copyProperties(parentMenu, menuVO);
            menuVO.setChildren(subMenuMap.getOrDefault(parentMenu.getId(), Collections.emptyList())
                    .stream()
                    .map(this::convertToMenuVO)
                    .toList());
            return menuVO;
        }).toList();
    }

    /**
     * 转换为菜单VO.
     * @param menu 菜单
     * @return MenuVO
     */
    private MenuVO convertToMenuVO(final Menu menu) {
        MenuVO menuVO = new MenuVO();
        BeanUtils.copyProperties(menu, menuVO);
        return menuVO;
    }
}
