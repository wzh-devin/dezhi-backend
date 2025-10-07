package com.devin.dezhi.ai.tools;

import com.devin.dezhi.common.annocation.AIParam;
import com.devin.dezhi.common.annocation.AITool;
import org.springframework.stereotype.Component;

/**
 * 2025/10/3 23:54.
 *
 * <p>
 * 天气工具
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class WeatherTools {

    /**
     * 获取天气.
     *
     * @param city 城市
     * @return 天气
     */
    @AITool(name = "get_weather", description = "获取城市天气")
    public String getWeather(
            @AIParam(name = "city", description = "城市", required = true) final String city
    ) {
        return "温度23度";
    }
}
