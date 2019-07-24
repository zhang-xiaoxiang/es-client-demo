package com.example.es.client.demo.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.example.es.client.demo.entity.User;
import com.example.es.client.demo.result.ResultData;
import com.example.es.client.demo.service.UserService;
import com.example.es.client.demo.util.MapUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * UserServiceImpl:实现类
 *
 * @author zhangxiaoxiang
 * @date: 2019/07/24
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    /**
     * Java高级别REST客户端
     */
    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private ResultData resultData;
    @Autowired
    private MapUtil esResponseUtil;

    /**
     * 新增一个User
     * map形式
     *
     * @param user
     * @return
     */
    @Override
    public User addUser(User user) {
        try {
            log.warn("方式1--------------------------------------------------------------------------------");
            //Json字符串作为数据源
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("name", user.getName());
            jsonMap.put("age", user.getAge());
            jsonMap.put("birthDay", new Date());
            //注意source要的是map
            IndexRequest indexRequest = new IndexRequest("user").id("1").source(jsonMap);
            //同步执行
            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
            client.close();
            //还可以进一步处理(后面的if 判断哪些代码,这里简洁形式)
            return user;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 新增一个User
     * 直接传json字符串
     *
     * @param user
     * @return
     */
    @Override
    public User addUser2(User user) {

        try {
            IndexRequest indexRequest = new IndexRequest("user");
            indexRequest.id("4");
            Object o = JSON.toJSON(user);
            System.out.println(o.toString());
            //或者下面的形式
            // String jsonString = "{" +
            //         "\"user\":\"kimchy\"," +
            //         "\"postDate\":\"2013-01-30\"," +
            //         "\"message\":\"trying out Elasticsearch\"" +
            //         "}";
            indexRequest.source(o.toString(), XContentType.JSON);
            //同步执行
            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
            client.close();
            return user;
        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }
    }


    /**
     * 新增一个User
     * 以映射形式提供的文档源，该映射将自动转换为JSON格式
     *
     * @param user
     * @return
     */
    @Override
    public User addUser3(User user) {
        log.warn("方式2--------------------------------------------------------------------------------");
        //以映射形式提供的文档源，该映射将自动转换为JSON格式
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.field("name", user.getName());
                builder.field("age", user.getAge());
                builder.field("birth_day", user.getBirthDay());
            }
            builder.endObject();
            IndexRequest indexRequest2 = new IndexRequest("user").id("2").source(builder);
            //同步执行
            IndexResponse indexResponse = client.index(indexRequest2, RequestOptions.DEFAULT);
            client.close();
            if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
                System.out.println("处理(如果需要)第一次创建文档的情况");

            } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
                System.out.println("处理(如果需要)将文档重写为已经存在的情况");
            }
            ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
            if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
                System.out.println("处理成功碎片的数量少于总碎片的情况");
            }
            if (shardInfo.getFailed() > 0) {
                for (ReplicationResponse.ShardInfo.Failure failure :
                        shardInfo.getFailures()) {
                    String reason = failure.reason();
                    System.out.println("处理潜在的故障");
                }
            }
            return user;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 新增一个User
     * 文档源作为XContentBuilder对象提供，Elasticsearch内置助手生成JSON内容
     *
     * @param user
     * @return
     */
    @Override
    public User addUser4(User user) {
        log.warn("方式3--------------------------------------------------------------------------------");
        try {
            //文档源作为XContentBuilder对象提供，Elasticsearch内置助手生成JSON内容
            IndexRequest indexRequest = new IndexRequest("user")
                    .id("3")
                    .source("name", user.getName(),
                            "birth_day", user.getBirthDay(),
                            "age", user.getAge());
            //同步执行
            //当以下列方式执行索引请求时，客户端在继续执行代码之前等待返回索引响应:
            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
            if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
                System.out.println("处理(如果需要)第一次创建文档的情况");

            } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
                System.out.println("处理(如果需要)将文档重写为已经存在的情况");
            }
            ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
            if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
                System.out.println("处理成功碎片的数量少于总碎片的情况");
            }
            if (shardInfo.getFailed() > 0) {
                for (ReplicationResponse.ShardInfo.Failure failure :
                        shardInfo.getFailures()) {
                    String reason = failure.reason();
                    System.out.println("处理潜在的故障");
                }
            }
            return user;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }
}
