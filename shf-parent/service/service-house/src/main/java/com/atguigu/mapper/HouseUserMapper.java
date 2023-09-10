package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.HouseUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @className: HouseUserMapper
 * @Package: com.atguigu.mapper

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
public interface HouseUserMapper extends BaseMapper<HouseUser> {

    /**
     * @Description 根据房源id查询房东信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param houseId
     * @return List<HouseUser>
     */
    List<HouseUser> selectHouseUserByHouseId(@Param("houseId") Long houseId);
}
