package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @className: RolePermissionMapper
 * @Package: com.atguigu.mapper

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    /**
     * @Description 根据roleId查询所有permissionId
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param roleId
     * @return List<Long>
     */
    List<Long> selectPermissionIdListByRoleId(Long roleId);

    /**
     * @Description 根据roleId和permissionIdList逻辑删除
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param roleId
     * @param removePermissionIds
     * @return int
     */
    int deleteListByRoleId(@Param("roleId") Long roleId, @Param("pids") List<Long> removePermissionIds);

    /**
     * @Description 根据roleId和permissionId查询权限信息, 不管是否删除
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param roleId
     * @param permissionId
     * @return Permission
     */
    RolePermission selectPermissionByRoleIdAndPermissionId(@Param("roleId") Long roleId, @Param("pid") Long permissionId);
}
