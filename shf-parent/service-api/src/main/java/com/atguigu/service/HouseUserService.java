package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.HouseUser;

import java.util.List;

/**
 * @className: HouseUserService
 * @Package: com.atguigu.service

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
public interface HouseUserService extends BaseService<HouseUser> {

    /**
     * @Description 根据房源id查询房东信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param houseId
     * @return List<HouseUser>
     */
    List<HouseUser> selectHouseUserByHouseId(Long houseId);

}


