package com.example.es.client.demo.controller;

import com.example.es.client.demo.entity.EsObject;
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
     * @param esObject
     * @return
     */
    @PostMapping("/select-index")
    public ResultData get(@RequestBody EsObject esObject) {
        // GetRequest getRequest = new GetRequest("test_index", esObject.getId());
        // GetResponse documentFields = esResponseUtil.get(getRequest);
        // //Does the document exists.判断文本时候存在(就是判断结果)
        // if (documentFields.isExists()) {
        //     return ResultResponse.success("查询成功!", documentFields.getSource());
        // }
        // return ResultResponse.success("查询成功,但是没有数据!");
        return null;
    }

    /**
     * 新增索引,注意新版本默认type为_doc
     *
     * 3种方式
     *
     * @return
     */
    @RequestMapping("/add-index")
    public ResultData putByMap(@RequestBody User user) {

        // User user1 = userService.addUser(user);
        //User user1 = userService.addUser2(user);
        // User user1 = userService.addUser3(user);
         User user1 = userService.addUser4(user);
        if (user1 == null) {
            return ResultResponse.failure("添加用户失败!");
        }
        return ResultResponse.success("测试新增!", user1);
    }


}
