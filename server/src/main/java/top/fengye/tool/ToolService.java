package top.fengye.tool;


import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

/**
 * @author: FengYe
 * @date: 2025/3/21 02:53
 * @description: ToolService
 */
@Service
public class ToolService {

    @Tool(description = "通过城市名称获取城市的天气")
    public String getWeather(String cityName) {
        return cityName + "当前天气阳光晴朗";
    }

    @Tool(description = "通过城市名称获取城市的时间")
    public String getTime(String cityName) {
        return cityName + "当前时间是早上 8 点";
    }
}
