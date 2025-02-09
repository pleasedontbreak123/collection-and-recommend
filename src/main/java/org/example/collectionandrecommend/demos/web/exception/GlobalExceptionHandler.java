package org.example.collectionandrecommend.demos.web.exception;

import org.example.collectionandrecommend.demos.web.response.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
/*
* @RestControllerAdvice =  @ControllerAdvice+@ResponseBody
* */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    // 处理自定义异常
    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<String> handleCustomException(CustomException e) {
        return Result.error(e.getCode(), e.getMessage());
    }

    // 处理其他所有异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<String> handleException(Exception e) {
        return Result.error("服务器内部错误: " + e.getMessage());
    }
}