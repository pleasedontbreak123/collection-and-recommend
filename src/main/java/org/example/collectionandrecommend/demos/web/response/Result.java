package org.example.collectionandrecommend.demos.web.response;

public class Result<T> {
    private int code; // 状态码
    private String message; // 返回信息
    private T data; // 返回数据

    // 私有构造方法，防止外部直接创建对象
    private Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 成功返回结果
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "成功", data);
    }

    // 成功返回结果，自定义消息
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(200, message, data);
    }

    // 失败返回结果
    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code, message, null);
    }

    // 失败返回结果，使用默认状态码
    public static <T> Result<T> error(String message) {
        return new Result<>(500, message, null);
    }

    // Getter 和 Setter 方法
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
