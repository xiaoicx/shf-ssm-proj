package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.HouseImage;

import java.util.List;

/**
 * @className: HouseImageService
 * @Package: com.atguigu.service

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
public interface HouseImageService extends BaseService<HouseImage> {

    /**
     * @Description 根据房源id查询房子图片信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param id
     * @param type
     * @return List<HouseImage>
     */
    List<HouseImage> selectHouseImageByType(Long id, Integer type);

    /**
     * @Description 根据id查询房源默认图片并且判断当前删除的图片是否是房源的默认图片, 是就双向删除不是就删除房源图片
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param id
     * @return int
     */
    int deleteHouseImageAndDeleteDefaultHouseImageById(Long id);
}
