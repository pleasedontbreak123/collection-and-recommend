package org.example.collectionandrecommend.demos.web.controller;

import org.example.collectionandrecommend.demos.web.utils.aop.LogAnnotation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TestController {


    @LogAnnotation(value = "执行 sayHello 方法")
    @GetMapping("/hello")
    public String sayHello(@RequestParam String name) {
        return "Hello, " + name;
    }

    @LogAnnotation(value = "执行 throwError 方法")
    @GetMapping("/error")
    public void throwError() {
        throw new RuntimeException("测试异常");
    }

}