package org.example.collectionandrecommend.demos.web.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
public class MyCorsFilter {
    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration corsConfiguration=new CorsConfiguration();
        //允许那些域访问
        corsConfiguration.addAllowedOrigin("*");
        //允许请求头字段
        corsConfiguration.addAllowedHeader("*");
        //允许请求的方法
        corsConfiguration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        //添加映射路径
        source.registerCorsConfiguration("/**",corsConfiguration);
        return new CorsFilter(source);
    }


}
