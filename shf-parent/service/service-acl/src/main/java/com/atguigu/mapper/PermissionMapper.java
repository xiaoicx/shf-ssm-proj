package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.Permission;

import java.util.List;

/**
 * @className: PermissionMapper
 * @Package: com.atguigu.mapper

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * @Description 根据parentId查询所有权限信息, 根据父id查询子节点
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param parentId
     * @return List<Permission>
     */
    List<Permission> selectPermissionListByParentId(Long parentId);

    /**
     * @Description 根据用户id查询id的权限信息和菜单
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param adminId
     * @return List<Permission>
     */
    List<Permission> selectPermissionListByAdminId(Long adminId);

    /**
     * @Description 根据name查询permission记录
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param name
     * @return Permission
     */
    Permission selectPermissionByName(String name);
}
