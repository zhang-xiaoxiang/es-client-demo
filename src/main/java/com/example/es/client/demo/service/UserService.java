package com.example.es.client.demo.service;

import com.example.es.client.demo.entity.User;


/**
 * UserService:用户服务层
 *
 * @author zhangxiaoxiang
 * @date: 2019/07/24
 */
public interface UserService {
    /**
     * 新增一个User
     * map方式
     * @param user
     * @return
     */
    User addUser(User user);
    /**
     * 新增一个User
     * 直接传json字符串--注意是json形式的字符串,最终是字符串
     * @param user
     * @return
     */
    User addUser2(User user);
    /**
     * 新增一个User
     * 以映射形式提供的文档源，该映射将自动转换为JSON格式
     * @param user
     * @return
     */
    User addUser3(User user);


    /**
     * 新增一个User
     * 文档源作为XContentBuilder对象提供，Elasticsearch内置助手生成JSON内容
     * @param user
     * @return
     */
    User addUser4(User user);

}
