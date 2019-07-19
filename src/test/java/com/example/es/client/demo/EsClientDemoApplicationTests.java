package com.example.es.client.demo;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsClientDemoApplicationTests {
    @Autowired
    private RestHighLevelClient client;

    @Test
    public void testX() {

    }


    /**
     * 测试创建索引
     */
    @Test
    public void test1() throws IOException {
        IndexRequest request = new IndexRequest(
                "posts",  // 索引 Index
                "doc",  // Type
                "1");  // 文档 Document Id
        String jsonString = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        request.source(jsonString, XContentType.JSON); // 文档源格式为 json string





    }

    @Test
    public void test2() throws IOException {
        GetRequest getRequest = new GetRequest(
                "posts",
                "doc",
                "1");
        GetResponse response =client.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(response.getSource());

    }

}
