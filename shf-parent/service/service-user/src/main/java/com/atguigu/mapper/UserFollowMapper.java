package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.UserFollow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @className: UserFollowMapper
 * @Package: com.atguigu.mapper

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
public interface UserFollowMapper extends BaseMapper<UserFollow> {

    /**
     * @Description 根据用户id和房源id查询是否关注房源
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param userId
     * @param houseId
     * @return UserFollow
     */
    UserFollow selectByUserIdAndHouseId(@Param("userId") Long userId, @Param("houseId") Long houseId);

    /**
     * @Description 根据用户id查询用户关注列表
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param userId
     * @return List<UserInfo>
     */
    List<UserFollow> selectListByUserId(Long userId);

    /**
     * @Description 根据关注的列表id查询具体的房源信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param userId
     * @param houseId
     * @return int
     */
    int deleteByUserIdAndHouseId(@Param("userId") Long userId, @Param("houseId") Long houseId);
}
