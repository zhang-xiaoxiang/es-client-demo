package com.example.es.client.demo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * User:用户对象
 *
 * @author zhangxiaoxiang
 * @date: 2019/07/19
 */
@Data
public class User implements Serializable {
    private String name;
    private Integer age;
    private Data birthDay;

}
