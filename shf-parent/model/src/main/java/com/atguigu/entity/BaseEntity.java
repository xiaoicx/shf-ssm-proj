package com.atguigu.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {

    //id
    private Long id;

    //创建时间
    private Date createTime;

    //跟新时间
    private Date updateTime;

    //是否逻辑删除
    private Integer isDeleted;
}
