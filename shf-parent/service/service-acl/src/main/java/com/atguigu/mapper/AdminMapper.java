package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @className: AdminMapper
 * @Package: com.atguigu.mapper

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
public interface AdminMapper extends BaseMapper<Admin> {

    /**
     * @Description 更具用户名查询用户
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
    List<Admin> selectOtherHouseBrokerByHouseId(@Param("houseId") Long houseId, @Param("brokerId") Long brokerId);
}
