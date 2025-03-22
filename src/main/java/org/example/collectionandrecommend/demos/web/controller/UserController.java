package org.example.collectionandrecommend.demos.web.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.collectionandrecommend.demos.web.model.dto.UserDto;
import org.example.collectionandrecommend.demos.web.model.vo.UserVo;
import org.example.collectionandrecommend.demos.web.response.Result;
import org.example.collectionandrecommend.demos.web.service.UserService;
import org.example.collectionandrecommend.demos.web.utils.aop.LogAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor_= { @Autowired})
public class UserController {

    private final UserService userService;

    @LogAnnotation("用户注册")
    @PostMapping("/register")
    public Result register(@RequestBody UserDto userDto){
        userService.register(userDto);
        return Result.success("注册成功");
    }

    @LogAnnotation("用户登录")
    @PostMapping("/login")
    public Result login(@RequestBody UserDto userDto){
        UserVo userVo = userService.login(userDto);
        return Result.success("登录成功", userVo);
    }
}