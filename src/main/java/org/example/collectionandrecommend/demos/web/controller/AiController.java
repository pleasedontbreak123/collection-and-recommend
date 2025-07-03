package org.example.collectionandrecommend.demos.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.collectionandrecommend.demos.web.model.dto.EventCategoryDto;
import org.example.collectionandrecommend.demos.web.response.Result;
import org.example.collectionandrecommend.demos.web.service.AiClient;
import org.example.collectionandrecommend.demos.web.service.EventService;
import org.example.collectionandrecommend.demos.web.utils.aop.LogAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor(onConstructor_= { @Autowired})
public class AiController {
    private final AiClient aiClient;

    @Operation(description = "提问ai")
    @LogAnnotation("提问ai")
    @GetMapping("/ask")
    public Result askAi(@RequestParam String question){

        try {
            // 调用 AiClient 获取 GPT 的响应
            String aiResponse = aiClient.getGptRes(question);

            // 返回成功的结果
            return Result.success("ai回答"+aiResponse);

        } catch (Exception e) {
            // 错误处理，返回失败信息
            return Result.error("ai请求失败"+e.getMessage());
        }
    }


}
