package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.Role;

import java.util.List;

/**
 * @className: RoleMappe
 * @Package: com.atguigu.mapper

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * @Description 根据adminid查询角色信息列表
     * @Since: 1.0.0
     * @Author xiaoqi

     * @param adminId
     * @return List<Role>
     */
    List<Role> selectRoleByAdminId(Long adminId);

}
