package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.UserFollow;
import com.atguigu.entity.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @className: UserFollowService
 * @Package: com.atguigu.service

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
public interface UserFollowService extends BaseService<UserFollow> {

    /**
     * @Description 根据用户id和房源id查询是否关注房源
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param userId
     * @param houseId
     * @return UserFollow
     */
    UserFollow selectByUserIdAndHouseId(Long userId, Long houseId);

    /**
     * @Description 根据用户id查询用户关注列表
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param userId
     * @return List<UserInfo>
     */
    List<UserFollow> selectListByUserId(Long userId);

    /**
     * @Description 根据userid 和houseid删除关注的房源
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param userId
     * @param houseId
     * @return int
     */
    int deleteByUserIdAndHouseId(Long userId, Long houseId);
}
