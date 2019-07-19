package com.example.es.client.demo.util;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * EsResponseUtil:请求ES返回后的数据的处理工具类
 *
 * @author zhangxiaoxiang
 * @date: 2019/07/19
 */
@Component
public class EsResponseUtil {
    /**
     * Java高级别REST客户端
     */
    @Autowired
    private RestHighLevelClient client;

    /**
     * 不带请求选项的的简单请求
     *
     * @param getRequest
     * @return
     */
    public  GetResponse get(GetRequest getRequest) {
        GetResponse documentFields = null;
        try {
            documentFields = client.get(getRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return documentFields;
    }
}
