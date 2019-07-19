package com.example.es.client.demo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * EsObject:封装返回的ES结果集
 *
 * @author zhangxiaoxiang
 * @date: 2019/07/19
 */
@Data
public class EsObject<T> implements Serializable {
    private String index;
    private String type;
    private String id;
    private String version;
    private String seqNo;
    private String primaryTerm;
    private Boolean found;
    private T source;
}
