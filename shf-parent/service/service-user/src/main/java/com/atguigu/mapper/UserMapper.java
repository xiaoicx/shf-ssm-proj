package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.UserInfo;
import org.apache.ibatis.annotations.Param;

/**
 * @className: UserMapper
 * @Package: com.atguigu.mapper

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
public interface UserMapper extends BaseMapper<UserInfo> {

    /**
     * @Description 根据手机号码查询用户
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param phone
     * @return int
     */
    UserInfo selectUserByPhone(@Param("phone") String phone);
}
