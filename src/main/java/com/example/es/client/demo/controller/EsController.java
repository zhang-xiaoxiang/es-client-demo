package com.example.es.client.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.es.client.demo.entity.EsObject;
import com.example.es.client.demo.entity.User;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
     * 查询索引
     * 根据ID查询索引
     * api按照索引/类型/id书写规范
     *
     * @param esObject
     * @return
     */
    @PostMapping("/test_index/product")
    public ResultData get(@RequestBody EsObject esObject) {
        GetRequest getRequest = new GetRequest("test_index", esObject.getId());
        GetResponse documentFields = esResponseUtil.get(getRequest);
        //Does the document exists.判断文本时候存在(就是判断结果)
        if (documentFields.isExists()) {
            return ResultResponse.success("查询成功!", documentFields.getSource());
        }
        return ResultResponse.success("查询成功,但是没有数据!");
    }

    /**
     * 新增索引
     * 注意查新建这个索引()的时候默认类型是
     *
     * @param user
     * @return
     */
    @PostMapping("/posts")
    public ResultData put(@RequestBody User user) {
        //Json字符串作为数据源
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("name", user.getName());
        jsonMap.put("age", user.getName());
        jsonMap.put("birthDay", new Date());
        //新增一个文档
        //注意source要的是map
        System.out.println(jsonMap);
        IndexRequest indexRequest = new IndexRequest("posts").id("1").source(jsonMap);
        try {
            IndexResponse indexResponse1 = client.index(indexRequest, RequestOptions.DEFAULT);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResultResponse.success("测试新增!", user);
    }


}
