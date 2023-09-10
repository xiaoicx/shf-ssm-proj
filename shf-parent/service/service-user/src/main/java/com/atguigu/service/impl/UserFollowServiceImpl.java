package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.UserFollow;
import com.atguigu.entity.UserInfo;
import com.atguigu.mapper.UserFollowMapper;
import com.atguigu.service.UserFollowService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @className: UserFollowServiceImpl
 * @Package: com.atguigu.service.impl

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
@Service(interfaceClass = UserFollowService.class)
public class UserFollowServiceImpl extends BaseServiceImpl<UserFollow> implements UserFollowService {

    @Autowired
    private UserFollowMapper userFollowMapper;

    @Override
    public BaseMapper<UserFollow> getBaseMapper() {
        return userFollowMapper;
    }

    @Override
    public UserFollow selectByUserIdAndHouseId(Long userId, Long houseId) {
        return userFollowMapper.selectByUserIdAndHouseId(userId, houseId);
    }

    @Override
    public List<UserFollow> selectListByUserId(Long userId) {
        return userFollowMapper.selectListByUserId(userId);
    }

    @Override
    public int deleteByUserIdAndHouseId(Long userId, Long houseId) {
        return userFollowMapper.deleteByUserIdAndHouseId(userId, houseId);
    }
}
