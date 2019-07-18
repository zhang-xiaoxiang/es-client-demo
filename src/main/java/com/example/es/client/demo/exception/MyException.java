package com.example.es.client.demo.exception;


import lombok.Data;

/**
 * MyException:自定义异常
 * @author zhangxiaoxiang
 * @date 2019/7/13
 */
@Data
public class MyException extends RuntimeException {

    /**
     * 自定义返回状态码
     */
    private Integer code;


    /**
     * 返回自定义的状态码和异常描述
     * @param code 自定义状态码
     * @param message 异常信息描述
     */
    public MyException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 只返回异常信息(默认返回500)
     * @param message 异常信息
     */
    public MyException(String message) {
        super(message);
    }




}
