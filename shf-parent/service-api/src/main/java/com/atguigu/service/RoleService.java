package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Role;

import java.util.List;


/**
 * @className: RoleService
 * @Package: com.atguigu.service

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
public interface RoleService extends BaseService<Role> {

    /**
     * @Description 根据adminid查询角色信息列表
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param adminId
     * @return List<Role>
     */
    List<Role> selectRoleByAdminId(Long adminId);

}
