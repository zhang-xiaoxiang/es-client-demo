package com.example.es.client.demo.controller;

import com.example.es.client.demo.exception.MyException;
import com.example.es.client.demo.result.ResultData;
import com.example.es.client.demo.result.ResultResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * EsController:ES 接口层
 *
 * @author zhangxiaoxiang
 * @date: 2019/07/18
 */
@RestController
public class EsController {
    /**
     * Java高级别REST客户端
     */
    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private ResultData resultData;


    @GetMapping("/test_index/product/{id}")
    public ResultData getOrder(@PathVariable("id") String id) {
        GetRequest getRequest = new GetRequest("test_index", "product", id);
        GetResponse response = null;
        try {
            response = client.get(getRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Does the document exists.判断文本时候存在(就是判断结果)
        if (response.isExists()) {
            return ResultResponse.success("查询成功!", response.getSource());
        }
        return ResultResponse.success("查询成功,但是没有数据!");


    }

    public static void main(String[] args) {

    }

}
