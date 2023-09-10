package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Admin;

import java.util.List;

/**
 * @className: AdminService
 * @Package: com.atguigu.service

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
public interface AdminService extends BaseService<Admin> {

    /**
     * @Description 根据用户名查询用户
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param admin
     * @return Admin
     */
    Admin selectByUserName(Admin admin);

    /**
     * @Description 查询当前房源未添加的经纪人信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param brokerId
     * @param houseId
     * @return List<Admin>
     */
    List<Admin> selectOtherHouseBrokerByHouseId(Long houseId,Long brokerId);

}
