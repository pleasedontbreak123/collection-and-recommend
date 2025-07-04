package org.example.collectionandrecommend.demos.web.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.collectionandrecommend.demos.web.exception.CustomException;
import org.example.collectionandrecommend.demos.web.model.dto.UserDto;
import org.example.collectionandrecommend.demos.web.model.dto.UserFavorDto;
import org.example.collectionandrecommend.demos.web.model.vo.UserVo;
import org.example.collectionandrecommend.demos.web.response.Result;
import org.example.collectionandrecommend.demos.web.service.RecommendService;
import org.example.collectionandrecommend.demos.web.service.Recommender;
import org.example.collectionandrecommend.demos.web.service.UserService;
import org.example.collectionandrecommend.demos.web.utils.Util.JwtUtil;
import org.example.collectionandrecommend.demos.web.utils.aop.LogAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor_= { @Autowired})
public class UserController {

    private final UserService userService;
    private final RecommendService recommendService;

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
        recommendService.init();
        return Result.success("登录成功", userVo);
    }

    @LogAnnotation("用户添加收藏")
    @PostMapping("/afterLogin/addFavor")
    public Result addFavor(@RequestBody UserFavorDto userFavorDto, HttpServletRequest req){
        String jwt = req.getHeader("token");

        try {
            Integer userId = Integer.valueOf(JwtUtil.parseToken(jwt));
            userFavorDto.setUserId(userId);
            userService.addFavor(userFavorDto);
            return Result.success("添加成功");
        }catch (Exception e){
            return Result.error("登录过期"+e.getMessage());
            //throw new CustomException(409, "登录过期"+e.getMessage());
        }

    }

    @LogAnnotation("获取用户收藏")
    @PostMapping("/afterLogin/listFavor")
    public Result addFavor(HttpServletRequest req){
        String jwt = req.getHeader("token");

        try {
            Integer userId = Integer.valueOf(JwtUtil.parseToken(jwt));
            List<Integer> list = userService.listFavor(userId);
            return Result.success("用户"+userId+"赛事:",list);
        }catch (Exception e){
            return Result.error("登录过期"+e.getMessage());
            //throw new CustomException(409, "登录过期"+e.getMessage());
        }

    }

    @LogAnnotation("获取用户推荐")
    @PostMapping("/afterLogin/recommend")
    public Result recommend(HttpServletRequest req){
        String jwt = req.getHeader("token");

        try {
            Integer userId = Integer.valueOf(JwtUtil.parseToken(jwt));

            List<Integer> recommendList = recommendService.recommendForUser(userId,5);
            return Result.success("推荐赛事列表",recommendList);
        }catch (Exception e){
            return Result.error("登录过期"+e.getMessage());
            //throw new CustomException(409, "登录过期"+e.getMessage());
        }

    }
}