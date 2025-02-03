package org.example.collectionandrecommend.demos.web.exception;

public class CustomException extends RuntimeException {
    private int code; // 自定义状态码

    public CustomException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}