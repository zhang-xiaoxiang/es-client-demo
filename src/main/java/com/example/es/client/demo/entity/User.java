package com.example.es.client.demo.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * User:用户对象
 *
 * @author zhangxiaoxiang
 * @date: 2019/07/19
 */
@Data
@NoArgsConstructor
public class User implements Serializable {
    private String userId;
    private String name;
    private Integer age;
    private Date birthday;

}
