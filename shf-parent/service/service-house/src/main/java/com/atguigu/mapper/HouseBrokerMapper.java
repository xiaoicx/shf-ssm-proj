package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.HouseBroker;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @className: HouseBrokerMapper
 * @Package: com.atguigu.mapper

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
public interface HouseBrokerMapper extends BaseMapper<HouseBroker> {

    /**
     * @Description 根据房源的id查询房源的经纪人列表
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param houseId
     * @return List<HouseBroker>
     */
    List<HouseBroker> selectHouseBrokerByHouseId(@Param("houseId") Long houseId);

    /**
     * @Description 根据旧的id修改经纪人的信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param houseBroker
     * @param oldId
     * @return void
     */
    int updateHouseBrokerByOldBrokerId(@Param("houseBroker") HouseBroker houseBroker, @Param("oldId") Long oldId);

    /**
     * @Description 根据houseId和brokerId逻辑删除
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param houseId
     * @param id
     * @return int
     */
    int deleteByHouseIdAndBrikerId(@Param("houseId") Long houseId, @Param("id") Long id);
}
