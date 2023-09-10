package com.atguigu.helper;

import com.atguigu.entity.Permission;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @className: PermissionHelper
 * @Package: com.atguigu.helper

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
public class PermissionHelper {

    /**
     * @Description 设置菜单的父子结构
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param permissionList
     * @return List<Permission>
     */
    public static List<Permission> buildMenu(List<Permission> permissionList) {

        List<Permission> menuList = null;

        //判断权限集合不为null
        if (!CollectionUtils.isEmpty(permissionList)) {

            //2. 从当前用户权限中筛选出一级菜单 parentId = 0
            menuList = permissionList.stream()
                    .filter(permission -> permission.getParentId() == 0)
                    .collect(Collectors.toList());

            //3. 对一级菜单构建子菜单 如果查询出的菜单中parent_id等于一级菜单中的id则是一级菜单的子菜单
            menuList.forEach(menuPermission -> {

                //设置一级菜单的级别
                menuPermission.setLevel(1);

                //递归设置子菜单
                setChildrenMenu(permissionList, menuPermission);
            });

        }

        return menuList;
    }

    /**
     * @Description 从permissionList中找出menuPermission的所有子菜单, 设置给menuPermission
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param permissionList
     * @param menuPermission
     * @return void
     */
    public static void setChildrenMenu(List<Permission> permissionList, Permission menuPermission) {

        //创建一个集合保存父级菜单的子菜单
        List<Permission> children = new ArrayList<>();

        //遍历全部的权限找出对应父级菜单的子菜单
        permissionList.forEach(child -> {
            //如果权限集合中的parent_id 等于 父级菜单中的id则是该父级菜单的子菜单
            if (child.getParentId().equals(menuPermission.getId())) {
                //设置子菜单的层级
                child.setLevel(menuPermission.getLevel() + 1);

                //设置子菜单的名称
//                child.setName(menuPermission.getName());

                //child是该父级菜单的子菜单
                children.add(child);

                //如果二级菜单还有子菜单则需要递归调用给二级菜单设置子菜单
                setChildrenMenu(permissionList, child);
            }
        });

        //给父级菜单设置子菜单集合
        menuPermission.setChildren(children);
    }
}
