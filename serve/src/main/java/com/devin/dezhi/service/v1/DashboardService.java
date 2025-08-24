package com.devin.dezhi.service.v1;

import com.devin.dezhi.domain.v1.vo.DashboardVO;

/**
 * 2025/8/24 20:52.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
public interface DashboardService {

    /**
     * 获取仪表盘数据.
     * @return 仪表盘数据
     */
    DashboardVO getDashboardInfo();
}
