package top.fengye;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import top.fengye.tool.ToolService;

/**
 * @author: FengYe
 * @date: 2025/3/21 02:53
 * @description: Main
 */
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public ToolCallbackProvider loadTools(ToolService toolService) {
        return MethodToolCallbackProvider.builder().toolObjects(toolService).build();
    }
}
