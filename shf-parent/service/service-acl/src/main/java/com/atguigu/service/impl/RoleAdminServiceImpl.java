package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.AdminRole;
import com.atguigu.mapper.RoleAdminMapper;
import com.atguigu.service.RoleAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @className: RoleAdminServiceImpl
 * @Package: com.atguigu.service.impl

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
@Slf4j
@Service(interfaceClass = RoleAdminService.class)
public class RoleAdminServiceImpl extends BaseServiceImpl<AdminRole> implements RoleAdminService {

    @Autowired
    private RoleAdminMapper roleAdminMapper;

    @Override
    public BaseMapper<AdminRole> getBaseMapper() {
        return roleAdminMapper;
    }

    @Override
    public List<AdminRole> selectListByAdminId(Long adminId) {
        return roleAdminMapper.selectListByAdminId(adminId);
    }

    @Override
    public int deleteByAdminIdAndRoleIdList(Long adminId, List<Long> roleListMapRoleId) {
        return roleAdminMapper.deleteByAdminIdAndRoleIdList(adminId, roleListMapRoleId);
    }

    @Override
    public AdminRole selectByAdminIdAndRoleId(Long adminId, Long roleId) {
        return roleAdminMapper.selectByAdminIdAndRoleId(adminId, roleId);
    }

    @Override
    //TODO 加强学习点1
    public void saveAssignRoleList(Long adminId, List<Long> roleIds) {

        //根据adminId查询所有已分配的角色
        List<AdminRole> roleListByAdminId = this.selectListByAdminId(adminId);

        //把查询出的RoleId归档成列表
        List<Long> roleListMapRoleId = roleListByAdminId.stream()
                .map(AdminRole::getRoleId)
                .collect(Collectors.toList());


        //需要移出的角色id
        List<Long> removeRoleIdList = new ArrayList<>();

        //如果查询到不为空则之前没有分配角色,
        if (!CollectionUtils.isEmpty(roleListMapRoleId)) {

            //如果前端传入的是空的角色集合则表示把之前的角色全部删除
            if (CollectionUtils.isEmpty(roleIds)) {
                removeRoleIdList = roleListMapRoleId;

            } else {//不为空那么检索出之前添加了这次没有添加的角色id

                for (Long roleId : roleListMapRoleId) {
                    //如果在roleListMapRoleId中不包含roleIds中传入的角色id则删除
                    if (!roleIds.contains(roleId)) {
                        removeRoleIdList.add(roleId);
                    }
                }
            }
        }

        //如果删除的集合不为空则删除之前存在但是现在不存在的roleid
        if (!CollectionUtils.isEmpty(removeRoleIdList)) {
            int i = this.deleteByAdminIdAndRoleIdList(adminId, removeRoleIdList);
            log.debug("删除的条数: rows={} 删除的ids={}", i, removeRoleIdList);
        }

        //如果roleIds为空则什么都不做
        if (!CollectionUtils.isEmpty(roleIds)) {

            //遍历新的需要新增的集合
            roleIds.forEach(roleId -> {

                AdminRole adminRole = this.selectByAdminIdAndRoleId(adminId, roleId);
                //如果查询到的不为null且之前删除了就表示曾经分配了角色
                if (!ObjectUtils.isEmpty(adminRole) && adminRole.getIsDeleted() == 1) {
                    adminRole.setIsDeleted(0);
                    this.update(adminRole);
                } else {
                    AdminRole role = new AdminRole();
                    role.setRoleId(roleId);
                    role.setAdminId(adminId);
                    this.insert(role);
                }
            });
        }
    }

}
