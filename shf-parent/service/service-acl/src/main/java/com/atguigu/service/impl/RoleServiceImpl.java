package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.Role;
import com.atguigu.mapper.RoleMapper;
import com.atguigu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @className: RoleServiceImpl
 * @Package: com.atguigu.service.impl

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
@Service(interfaceClass = RoleService.class)
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;


    @Override
    public BaseMapper<Role> getBaseMapper() {
        return roleMapper;
    }

    @Override
    public List<Role> selectRoleByAdminId(Long adminId) {
        return roleMapper.selectRoleByAdminId(adminId);
    }
}
