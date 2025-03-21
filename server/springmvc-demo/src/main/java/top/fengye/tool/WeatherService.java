package top.fengye.tool;


import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * @author: FengYe
 * @date: 2025/3/22 02:49
 * @description: WeatherService
 */
@Service
public class WeatherService {
    @Tool(description = "通过cityName城市名称获取天气信息")
    public String getWeather(String cityName) {
        return "温度：20度，天气：晴，湿度：50%";
    }
}
