package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Permission;

import java.util.List;
import java.util.Map;

/**
 * @className: PermissionService
 * @Package: com.atguigu.service

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
public interface PermissionService extends BaseService<Permission> {

    /**
     * @Description 根据roleid查询权限信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param roleId
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> findPermissionIdByRoleId(Long roleId);


    /**
     * @Description 根据roleid保存权限列表
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param roleId
     * @param permissionIds
     * @return void
     */
    void saveAssignPermission(Long roleId, List<Long> permissionIds);


    /**
     * @Description 更具用户信息查询出用户的权限列表和权限菜单
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param adminId
     * @return List<Permission>
     */
    List<Permission> selectMenuPermissionListByAdminId(Long adminId);

    /**
     * @Description 查询所有权限信息并且构建菜单
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param
     * @return List<Permission>
     */
    List<Permission> findAllMenu();

    /**
     * @Description 根据用户名id查询对应的用户权限代码
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param userId
     * @return List<String>
     */
    List<String> selectPermissionCodeListByUserId(Long userId);
}
