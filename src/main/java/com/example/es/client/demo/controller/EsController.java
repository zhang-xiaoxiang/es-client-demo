package com.example.es.client.demo.controller;

import com.example.es.client.demo.entity.User;
import com.example.es.client.demo.result.ResultData;
import com.example.es.client.demo.result.ResultResponse;
import com.example.es.client.demo.service.UserService;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * EsController:ES 接口层
 * 参考官方7.2给出的API
 * https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high-supported-apis.html
 * 网友的参考
 * https://blog.csdn.net/zhangshng/article/details/95946596
 *
 *
 * @author zhangxiaoxiang
 * @date: 2019/07/18
 */
@RestController
@RequestMapping("/user")
public class EsController {
    @Autowired
    private UserService userService;

    /**
     * 查询索引
     * 根据ID查询索引
     * api按照索引/类型/id书写规范
     *
     * @param user
     * @return
     */
    @PostMapping("/get-index")
    public ResultData get(@RequestBody User user) {
        GetResponse getResponse = userService.getUser(user);
        if (getResponse == null) {
            return ResultResponse.failure("查询失败!");
        }
        //返回想要的
        return ResultResponse.success("查询成功!", getResponse.getSourceAsMap());
    }

    /**
     * 新增索引,注意新版本默认type为_doc
     * <p>
     * 4种方式(萝卜青菜各有所爱)
     *
     * @return
     */
    @RequestMapping("/add-index")
    public ResultData putByMap(@RequestBody User user) {
        User user1 = userService.addUser2(user);
        if (user1 == null) {
            return ResultResponse.failure("添加用户失败!");
        }
        return ResultResponse.success("新增成功!", user1);
    }

    /**
     * 删除索引--同步删除
     * <p></p>
     *
     * @return
     */
    @RequestMapping("/del-index")
    public ResultData delIndex(@RequestBody User user) {
        DeleteResponse deleteResponse = null;
        try {
            deleteResponse = userService.delUser(user);
            if (deleteResponse.getShardInfo().getSuccessful() > 0) {
                //需要返回的信息在deleteResponse对象里面找
                return ResultResponse.success("删除成功!", "ES主键: " +
                        deleteResponse.getId() + "  ES索引:" + deleteResponse.getShardId().getIndexName());
            }

            return ResultResponse.failure("删除用户失败!");

        } catch (Exception e) {
            e.printStackTrace();
            return ResultResponse.failure("删除用户失败!");
        }

    }

    /**
     * 异步删除索引
     *
     * @param user
     * @return
     */
    @RequestMapping("/del-asy-index")
    public ResultData delByAsyIndex(@RequestBody User user) {
        Integer integer;

        integer = userService.delUserByAsy(user);
        if (1 == integer) {
            //需要返回的信息在deleteResponse对象里面找
            return ResultResponse.success("删除成功!");
        }
        return ResultResponse.failure("删除用户失败!");


    }

    /**
     * 更新用户(索引)
     *
     * @param user
     * @return
     */
    @RequestMapping("/upd-index")
    public ResultData updUser(@RequestBody User user) {
        UpdateResponse response = userService.updUser(user);
        if (response!=null) {
            return ResultResponse.success("更新成功!");
        }
        return ResultResponse.failure("更新用户失败!");


    }

}
