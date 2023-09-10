package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.AdminRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @className: RoleAdminMapper
 * @Package: com.atguigu.mapper

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
public interface RoleAdminMapper extends BaseMapper<AdminRole> {


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
    int deleteByAdminIdAndRoleIdList(@Param("adminId") Long adminId, @Param("roleIds") List<Long> roleListMapRoleId);


    /**
     * @Description 根据adminId和RoleId查询角色不管是否删除
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param adminId
     * @param roleId
     * @return AdminRole
     */
    AdminRole selectByAdminIdAndRoleId(@Param("adminId")Long adminId,@Param("roleId") Long roleId);
}
