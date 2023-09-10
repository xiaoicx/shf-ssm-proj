package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.House;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @className: HouseMapper
 * @Package: com.atguigu.mapper

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
public interface HouseMapper extends BaseMapper<House> {

    /**
     * @Description 根据communityId查询房源数量, 未删除的
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param id
     * @return Long
     */
    Long selectHouseTotalByCommunityId(@Param("id") Long id);

    /**
     * @Description 根据id发布房源或者取消发布
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param id
     * @param status
     * @return void
     */
    Integer publishHouseById(@Param("id") Long id, @Param("status") Short status);


    /**
     * @Description 根据id 跟新房源的默认图片
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param id
     * @param imgUrl
     * @return Integer
     */
    Integer updateDefaultImageUrlByHouseId(@Param("id") Long id, @Param("imgUrl") String imgUrl);

    /**
     * @Description 根据id查询默认图片
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param id
     * @return String
     */
    public String selectDefaultHouseImageUrlByHouseId(@Param("id") Long id);


    /**
     * @Description 根据条件分页查询房源信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param houseQueryVo
     * @return List<HouseVo>
     */
    List<HouseVo> selectHousePageInfoByHouseQueryVo(HouseQueryVo houseQueryVo);

    /**
     * @Description 根据关注的列表id查询具体的房源信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param houseListId
     */
    List<HouseVo> selectHousePageInfoByHouseIdList(@Param("list") List<Long> houseListId);

}
