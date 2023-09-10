package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.UserInfo;
import com.atguigu.mapper.UserMapper;
import com.atguigu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @className: UserServiceImpl
 * @Package: com.atguigu.service.impl

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
@Service(interfaceClass = UserService.class)
public class UserServiceImpl extends BaseServiceImpl<UserInfo> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public BaseMapper<UserInfo> getBaseMapper() {
        return userMapper;
    }

    @Override
    public UserInfo selectUserByPhone(String phone) {
        return userMapper.selectUserByPhone(phone);
    }
}
