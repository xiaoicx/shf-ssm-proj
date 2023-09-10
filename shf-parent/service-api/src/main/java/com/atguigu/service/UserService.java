package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.UserInfo;

/**
 * @className: UserService
 * @Package: com.atguigu.service

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
public interface UserService extends BaseService<UserInfo> {

    /**
     * @Description 根据手机号码查询用户
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param phone
     * @return int
     */
    UserInfo selectUserByPhone(String phone);



}
