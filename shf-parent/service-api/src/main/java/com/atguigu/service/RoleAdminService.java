package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.AdminRole;

import java.util.List;

/**
 * @className: RoleAdminService
 * @Package: com.atguigu.service

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
public interface RoleAdminService extends BaseService<AdminRole> {

    /**
     * @Description 根据用户id查询用户角色列表
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param adminId
     * @return List<AdminRole>
     */
    List<AdminRole> selectListByAdminId(Long adminId);

    /**
     * @Description 根据adminId和RoleIdlist删除分配的角色
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param adminId
     * @param roleListMapRoleId
     * @return int
     */
    int deleteByAdminIdAndRoleIdList(Long adminId, List<Long> roleListMapRoleId);

    /**
     * @Description 根据adminId和RoleIdlist保存角色
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param adminId
     * @param roleIds
     * @return void
     */
    void saveAssignRoleList(Long adminId, List<Long> roleIds);

    /**
     * @Description 根据adminId和RoleId查询对应的角色信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param adminId
     * @param roleId
     * @return AdminRole
     */
    AdminRole selectByAdminIdAndRoleId(Long adminId, Long roleId);
}
