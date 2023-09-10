package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.House;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @className: HouseService
 * @Package: com.atguigu.service

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
public interface HouseService extends BaseService<House> {

    /**
     * @Description 根据communityId查询房源数量
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param communityId
     * @return Long
     */
    Long selectHouseTotalByCommunityId(Long communityId);

    /**
     * @Description 根据id发布房源或者取消发布
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param id
     * @param status
     * @return void
     */
    int publishHouseById(Long id, Short status);

    /**
     * @Description 根据房源id跟新默认图片
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param houseId
     * @return int
     */
    int updateDefaultImageUrlByHouseId(Long houseId, String imageUrl);

    /**
     * @Description 根据id查询默认图片
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param houseId
     * @return String
     */
    String selectDefaultHouseImageUrlByHouseId(Long houseId);

    /**
     * @Description 根据查询条件分页查询房源信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param pageNum
     * @param pageSize
     * @param houseQueryVo
     * @return PageInfo<HouseVo>
     */
    PageInfo<HouseVo> selectHousePageInfoByHouseQueryVo(Integer pageNum, Integer pageSize, HouseQueryVo houseQueryVo);


    /**
     * @Description 根据关注的列表id查询具体的房源信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param pageNum
     * @param pageSize
     * @param houseListId
     */
    PageInfo<HouseVo> selectHouseVoListByUserFollowList(int pageNum, int pageSize, List<Long> houseListId);
}
