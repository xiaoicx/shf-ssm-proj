package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Admin;
import com.atguigu.entity.HouseBroker;

import java.util.List;

/**
 * @className: HouseBrokerService
 * @Package: com.atguigu.service

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
public interface HouseBrokerService extends BaseService<HouseBroker> {

    /**
     * @Description 根据房源的id查询房源的经纪人列表
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param houseId
     * @return List<HouseBroker>
     */
    List<HouseBroker> selectHouseBrokerByHouseId(Long houseId);

    /**
     * @Description 根据旧的id修改经纪人的信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param houseBroker
     * @param oldId
     * @return void
     */
    int updateHouseBrokerByOldBrokerId(HouseBroker houseBroker, Long oldId);

    /**
     * @Description 根据houseId和brokerId逻辑删除
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param houseId
     * @param brokerId
     * @return int
     */
    int deleteByHouseIdAndBrikerId(Long houseId, Long brokerId);
}
