package com.atguigu.service.impl;

import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.RolePermission;
import com.atguigu.mapper.RolePermissionMapper;
import com.atguigu.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @className: RolePermissionServiceImpl
 * @Package: com.atguigu.service.impl

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
public class RolePermissionServiceImpl extends BaseServiceImpl<RolePermission> implements RolePermissionService {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public BaseMapper<RolePermission> getBaseMapper() {
        return rolePermissionMapper;
    }


}
