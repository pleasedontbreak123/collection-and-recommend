package org.example.collectionandrecommend.demos.web.utils.aop;

import java.lang.annotation.*;





/*
* 在方法前加上@LogAnnotation注解
* 将会自动打印统一日志输出
*
* */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
    String value() default "";
}