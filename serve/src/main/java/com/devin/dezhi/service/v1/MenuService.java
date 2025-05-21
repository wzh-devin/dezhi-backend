package com.devin.dezhi.service.v1;

import com.devin.dezhi.domain.v1.vo.MenuVO;
import java.util.List;

/**
 * 2025/5/22 1:37.
 *
 * <p></p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
public interface MenuService {

    /**
     * 获取菜单数据.
     * @return List
     */
    List<MenuVO> getMenuData();
}
