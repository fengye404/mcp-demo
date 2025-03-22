package top.fengye;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.transport.WebMvcSseServerTransport;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import top.fengye.tool.WeatherService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class Main {
    private static final String MESSAGE_ENDPOINT = "/mcp/message";

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public ToolCallbackProvider weatherTools(WeatherService weatherService) {
        return MethodToolCallbackProvider.builder().toolObjects(weatherService).build();
    }

    @Bean
    public List<McpServerFeatures.SyncResourceRegistration> myResources() {
        List<McpServerFeatures.SyncResourceRegistration> registrations = new ArrayList<>();

        var systemInfoResource = new McpSchema.Resource("system://info",
                "System Information",
                "Provides basic system information including Java version, OS, etc.",
                "application/json", null);
        var resourceRegistration = new McpServerFeatures.SyncResourceRegistration(systemInfoResource, request -> {
            try {
                var systemInfo = Map.of(
                        "javaVersion", System.getProperty("java.version"),
                        "osName", System.getProperty("os.name"),
                        "osVersion", System.getProperty("os.version"),
                        "osArch", System.getProperty("os.arch"),
                        "processors", Runtime.getRuntime().availableProcessors(),
                        "timestamp", System.currentTimeMillis());
                String jsonContent = new ObjectMapper().writeValueAsString(systemInfo);
                return new McpSchema.ReadResourceResult(
                        List.of(new McpSchema.TextResourceContents(request.uri(), "application/json", jsonContent)));
            }
            catch (Exception e) {
                throw new RuntimeException("Failed to generate system info", e);
            }
        });
        registrations.add(resourceRegistration);

        var apiListInfo = new McpSchema.Resource("api://list",
                "开放平台的 api 列表",
                "开放平台的 api 列表",
                "application/json", null);
        var apiListInfoRegistration = new McpServerFeatures.SyncResourceRegistration(apiListInfo, request -> {
            try {
                var systemInfo = Map.of(
                        "发品", "publishArticle",
                        "铺货", "puhuo",
                        "开店", " kaidian");
                String jsonContent = new ObjectMapper().writeValueAsString(systemInfo);
                return new McpSchema.ReadResourceResult(
                        List.of(new McpSchema.TextResourceContents(request.uri(), "application/json", jsonContent)));
            }
            catch (Exception e) {
                throw new RuntimeException("Failed to generate system info", e);
            }
        });
        registrations.add(apiListInfoRegistration);
        return registrations;
    }
}