package com.example.es.client.demo.controller;

import com.example.es.client.demo.entity.EsObject;
import com.example.es.client.demo.result.ResultData;
import com.example.es.client.demo.result.ResultResponse;
import com.example.es.client.demo.util.EsResponseUtil;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private EsResponseUtil esResponseUtil;

    /**
     * 根据ID查询索引文档
     * api按照索引/类型/id书写规范
     * @param esObject
     * @return
     */
    @PostMapping("/test_index/product")
    public ResultData find(@RequestBody EsObject esObject) {
        GetRequest getRequest = new GetRequest("test_index", esObject.getId());
        GetResponse documentFields = esResponseUtil.get(getRequest);
        //Does the document exists.判断文本时候存在(就是判断结果)
        if (documentFields.isExists()) {
            return ResultResponse.success("查询成功!", documentFields.getSource());
        }
        return ResultResponse.success("查询成功,但是没有数据!");
    }

    @PostMapping("/test_index/product/{id}")
    public ResultData add(@PathVariable("id") String id) {
        System.out.println("进入--------------------");
        //Json字符串作为数据源
        IndexRequest indexRequest1 = new IndexRequest(
                "test_index");
        String jsonString = "{" +
                "\"name\":\"张大仙\"," +
                "\"mother\":\"蒋美碧\" }";
        indexRequest1.source(jsonString, XContentType.JSON);
        try {
            IndexResponse indexResponse1 = client.index(indexRequest1, RequestOptions.DEFAULT);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResultResponse.success("测试新增!");
    }






}
