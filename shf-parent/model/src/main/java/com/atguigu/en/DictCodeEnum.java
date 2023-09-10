package com.atguigu.en;

/**
 * @className: DictCodeEnum
 * @Package: com.atguigu.en

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
public enum DictCodeEnum {

    HOUSETYPE("houseType"),
    FLOOR("floor"),
    BUILDSTRUCTURE("buildStructure"),
    DECORATION("decoration"),
    DIRECTION("direction"),
    HOUSEUSE("houseUse"),
    PROVINCE("province"),
    BEIJING("beijing");


    private String dictCode;

    DictCodeEnum(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getDictCode() {
        return dictCode;
    }
}
