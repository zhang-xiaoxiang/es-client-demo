package com.example.es.client.demo.controller;

import com.example.es.client.demo.entity.User;
import com.example.es.client.demo.result.ResultData;
import com.example.es.client.demo.result.ResultResponse;
import com.example.es.client.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * EsController:ES 接口层
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
        User user1 = userService.getUser(user);
        if (user1 == null) {
            return ResultResponse.failure("查询失败!");
        }
        return ResultResponse.success("查询成功!", user1);
    }

    /**
     * 新增索引,注意新版本默认type为_doc
     *
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


}
