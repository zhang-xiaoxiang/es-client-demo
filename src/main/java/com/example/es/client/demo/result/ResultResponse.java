package com.example.es.client.demo.result;

/**
 * ResultResponse:封装的返回数据的常用方法\
 * 是spring最后处理的对象,然后返回给其前端(客户端)
 *
 * @author zhangxiaoxiang
 * @date: 2019/07/18
 */
public class ResultResponse {

    /**
     * 成功返回响应码和数据
     *
     * @param msg 输入成功描述信息给客户端
     * @return
     */
    public static ResultData success(String msg) {
        ResultData ResultData = new ResultData();
        ResultData.setCode(200);
        ResultData.setMsg(msg);
        ResultData.setData(null);
        return ResultData;
    }

    /**
     * 成功返回响应码,信息和数据
     *
     * @param msg  输入成功描述信息
     * @param data 输入返回给客户端的数据
     * @return
     */
    public static ResultData success(String msg, Object data) {
        ResultData ResultData = new ResultData();
        ResultData.setCode(200);
        ResultData.setMsg(msg);
        ResultData.setData(data);
        return ResultData;
    }

    /**
     * 失败返回自定义的code和异常信息
     *
     * @param code 输入失败状态码(自定义异常才会用到)
     * @param msg  输入失败原因
     * @return
     */
    public static ResultData failure(Integer code, String msg) {
        ResultData ResultData = new ResultData();
        ResultData.setCode(code);
        ResultData.setMsg(msg);
        ResultData.setData(null);
        return ResultData;
    }

    /**
     * 失败返回状态码和
     *
     * @param msg 输入失败原因
     * @return
     */
    public static ResultData failure(String msg) {
        ResultData ResultData = new ResultData();
        ResultData.setCode(500);
        ResultData.setMsg(msg);
        ResultData.setData(null);
        return ResultData;
    }
}
