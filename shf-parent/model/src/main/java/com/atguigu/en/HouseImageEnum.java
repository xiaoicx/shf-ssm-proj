package com.atguigu.en;

/**
 * @className: HouseImageEnum
 * @Package: com.atguigu.en

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
public enum HouseImageEnum {
    HOUSE_SOURCE(1),
    HOUSE_PROPERTY(2);

    private Integer type;

    HouseImageEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
