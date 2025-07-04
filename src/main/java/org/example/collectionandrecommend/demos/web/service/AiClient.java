package org.example.collectionandrecommend.demos.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class AiClient {


//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
    //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
//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
    private static final String apiKey = "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";



    private final WebClient webClient;
    // 构造函数通过 WebClient.Builder 自动注入 WebClient 实例
    public AiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://openrouter.ai/api/v1/chat/completions").build();
    }

    private List<JsonNode> messages = new ArrayList<>();




    public String getGptRes(String quest) throws Exception{


        // 构建提示词
        ObjectMapper objectMapper = new ObjectMapper();



        this.messages.add(objectMapper.createObjectNode().put("role", "user")
                .put("content", quest));

        //构建请求
        JsonNode requestBody = objectMapper.createObjectNode()
                .put("model", "qwen/qwen-2.5-coder-32b-instruct:free")
                //.put("max_tokens", 2000)  // 设置更大的最大 token 数
                .set("messages", objectMapper.valueToTree(messages));

        String responseBody = sendRequest(requestBody);
        System.out.println(responseBody);
        String res =  parseResponse(responseBody);
        this.messages.add(objectMapper.createObjectNode().put("role", "assistant")
                .put("content", res));
        return res;
    }


    private String sendRequest(JsonNode requestBody) {
        return webClient.post()  // 发起 POST 请求
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)  // 请求体
                .retrieve()
                .bodyToMono(String.class)  // 将响应体转为 String 类型
                .block();  // 阻塞，等待响应
    }


    // 解析返回的 JSON 响应
    private String parseResponse(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseNode = objectMapper.readTree(responseBody);

            // 获取 'choices' 字段
            JsonNode choicesNode = responseNode.path("choices");
            if (choicesNode.isArray() && choicesNode.size() > 0) {
                JsonNode messageNode = choicesNode.get(0).path("message");
                return messageNode.path("content").asText();
            } else {
                throw new RuntimeException("Invalid response format: 'choices' is empty or missing");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error parsing response", e);
        }
    }
}
