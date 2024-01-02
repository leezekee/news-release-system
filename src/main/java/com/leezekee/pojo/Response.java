package com.leezekee.pojo;


//统一响应结果

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Response {
    private Integer code;//业务状态码  0-成功  1-失败
    private String message;//提示信息
    private Object data;//响应数据

    public static Response success(String message) {
        return new Response(Code.SUCCESS, message, null);
    }

    public static Response success(String message, Object data) {
        return new Response(Code.SUCCESS, message, data);
    }

    public static Response success(int code, String message) {
        return new Response(code, message, null);
    }

    public static Response success(int code, String message, Object data) {
        return new Response(code, message, data);
    }

    public static Response error(String message) {
        return new Response(Code.UNKNOWN_ERROR, message, null);
    }
    public static Response error(int code, String message) {
        return new Response(code, message, null);
    }
    public static Response error(int code, String message, Object data) {
        return new Response(code, message, data);
    }
}
