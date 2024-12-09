package com.zhifou.tools;

import lombok.Data;

@Data
public class Response<T> {
    private int code; // 状态码
    private String message; // 提示信息
    private T data; // 数据对象

    // 构造方法
    public Response(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 静态方法：成功响应
    public static <T> Response<T> success(T data) {
        return new Response<>(200, "操作成功", data);
    }

    public static <T> Response<T> success(String message, T data) {
        return new Response<>(200, message, data);
    }

    // 静态方法：错误响应
    public static <T> Response<T> error(int code, String message) {
        return new Response<>(code, message, null);
    }

    public static <T> Response<T> error(int code, String message, T data) {
        return new Response<>(code, message, data);
    }


}
