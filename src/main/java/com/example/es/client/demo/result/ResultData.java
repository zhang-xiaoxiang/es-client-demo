package com.example.es.client.demo.result;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;


/**
 * ResultData:REST API 接口访问返回的对象
 * spring mvc会帮我们转换成json格式
 *
 * @author zhangxiaoxiang
 * @date 2019/7/18
 */

@Data
@Component
public class ResultData<T> implements Serializable {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 提示信息
     */
    private String msg;

    /**
     * 返回的具体数据
     * 如果为空不反回那么使用注解
     *
     * @JsonInclude(JsonInclude.Include.NON_NULL)
     */
    private T data;



}
