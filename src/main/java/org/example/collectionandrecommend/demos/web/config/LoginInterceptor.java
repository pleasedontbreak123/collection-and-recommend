package org.example.collectionandrecommend.demos.web.config;

//import com.alibaba.fastjson.JSONObject;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.example.collectionandrecommend.demos.web.response.Result;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.example.collectionandrecommend.demos.web.utils.Util.JwtUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override//方法运行前运行
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        System.out.println("preHandle......");
        String url = req.getRequestURL().toString();
        log.info("url :",url);


        if(url.contains("login")){
            return true;
        }

        String jwt = req.getHeader("token");
        log.info(jwt);

        if(!StringUtils.hasLength(jwt)){
            log.info("请求头为空，未登录");

            Result result = Result.error("Not Login");
            String unLOGIN = JSONObject.toJSONString(result);
            res.getWriter().write(unLOGIN);

            return false;
        }

        try {
            JwtUtil.parseToken(jwt);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("解析失败");
            Result result = Result.error("Not Login");
            String unLOGIN = JSONObject.toJSONString(result);
            res.getWriter().write(unLOGIN);
            return false;
        }


        log.info("解析成功，放行");

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle...");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion...");
    }
}
