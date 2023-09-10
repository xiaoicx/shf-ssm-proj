package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.Permission;
import com.atguigu.entity.RolePermission;
import com.atguigu.helper.PermissionHelper;
import com.atguigu.mapper.PermissionMapper;
import com.atguigu.mapper.RolePermissionMapper;
import com.atguigu.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @className: PermissionServiceImpl
 * @Package: com.atguigu.service.impl

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
@Slf4j
@Service(interfaceClass = PermissionService.class)
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public BaseMapper<Permission> getBaseMapper() {
        return permissionMapper;
    }


    @Override
    public List<Map<String, Object>> findPermissionIdByRoleId(Long roleId) {

        //1. 查询所有的权限信息
        List<Permission> allPermissionList = permissionMapper.findAll();

        //3. 查询当前角色分配了哪些权限
        List<Long> permissionIdListByRoleId = rolePermissionMapper.selectPermissionIdListByRoleId(roleId);

        //2. 把查询出的permission映射为5个键值对的map { id:1, pId:0, name:"随意勾选 1", open:true, checked:true}
        ArrayList<Map<String, Object>> zNodes = new ArrayList<>();
        allPermissionList.forEach(permission -> {

            //创建map
            HashMap<String, Object> node = new HashMap<>();
            //对map进行赋值
            node.put("id", permission.getId());
            node.put("pId", permission.getParentId());
            node.put("name", permission.getName());

            //open属性, 则是判断当前id是否有子节点, 如果有子节点就设置为true 没有就不设置
            //用当前节点的id查parent_id 不为空则代表有子节点 为空则没有子节点
            List<Permission> permissionListByParentId = permissionMapper.selectPermissionListByParentId(permission.getId());
            if (!CollectionUtils.isEmpty(permissionListByParentId)) {
                node.put("open", true);
            }

            //当前角色id哪些权限被选中了
            node.put("checked", permissionIdListByRoleId.contains(permission.getId()));

            //添加节点到列表
            zNodes.add(node);
        });

        return zNodes;
    }

    @Override
    public synchronized void saveAssignPermission(Long roleId, List<Long> permissionIds) {
        //1. 根据roleId查询出角色对应的permissionId列表
        List<Long> permissionIdListByroleId = rolePermissionMapper.selectPermissionIdListByRoleId(roleId);

        //需要删除的permissionId列表
        List<Long> removePermissionIds = new ArrayList<>();

        //2. 如果当前查询的权限信息不为空 则找出当前需要删除的id
        if (!CollectionUtils.isEmpty(permissionIdListByroleId)) {

            //3. 如果当前传入的id为null 表示之前的权限信息都是需要删除的
            if (CollectionUtils.isEmpty(permissionIds)) {
                removePermissionIds = permissionIdListByroleId;

            } else {// 查询的结果不为空, 找出需要删除的permissionId

                //遍历查询出的permissionId
                for (Long permissionId : permissionIdListByroleId) {
                    //如果新增的permissionIds不包含从数据库中查询到的则是要删除的
                    if (!permissionIds.contains(permissionId)) {
                        removePermissionIds.add(permissionId);
                    }
                }
            }
        }

        //如果需要删除的集合列表不为空
        if (!CollectionUtils.isEmpty(removePermissionIds)) {
            //根据roleId和removePermissionIds逻辑删除
            int r1 = rolePermissionMapper.deleteListByRoleId(roleId, removePermissionIds);
            log.debug("删除的权限条数: rows={}  roleId={}", r1, roleId);
        }

        //如果传入的permissionId为空则不需要保存
        if (!CollectionUtils.isEmpty(permissionIds)) {

            //遍历需要新增的permissionId列表进行新增
            permissionIds.forEach(permissionId -> {

                //查询出当前的permissionId之前是否存在过
                RolePermission permission = rolePermissionMapper.selectPermissionByRoleIdAndPermissionId(roleId, permissionId);
                if (!ObjectUtils.isEmpty(permission) && permission.getIsDeleted().equals(1)) {
                    //之前存在过但是被删除了
                    permission.setIsDeleted(0);
                    rolePermissionMapper.update(permission);

                } else {
                    //不存在则新增
                    permission = new RolePermission();
                    permission.setRoleId(roleId);
                    permission.setPermissionId(permissionId);
                    rolePermissionMapper.insert(permission);
                }
            });
        }
    }

    @Override
    public List<Permission> selectMenuPermissionListByAdminId(Long adminId) {

        //0. 判断当前用户是否是超级管理员, 如果是超级管理员则查出所有权限
        List<Permission> permissionList = null;
        if (adminId == 1) {

            //查询出所有权限
            permissionList = permissionMapper.findAll();

        } else {
            //1. 如果不是超级管理员则查询出当前用户的所有权限
            permissionList = permissionMapper.selectPermissionListByAdminId(adminId);
        }
        //构建菜单和菜单的父子结构
        return PermissionHelper.buildMenu(permissionList);
    }

    @Override
    public List<Permission> findAllMenu() {
        //1. 查询所有权限信息
        List<Permission> permissionList = permissionMapper.findAll();

        //2. 判断权限信息不为空
        if (CollectionUtils.isEmpty(permissionList)) {
            throw new RuntimeException("权限信息为空!");
        }

        //3. 构建菜单
        return PermissionHelper.buildMenu(permissionList);
    }


    /**
     * @Description 重写insert方法, 需要对插入的菜单进行判断是否重复插入
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param permission
     * @return int
     * Permission(parentId=0, name=订单管理, url=/order, code=null, type=1, sort=1, level=null, children=null, parentName=null)
     */
    @Override
    public int insert(Permission permission) {
        //根据插入的permissionName查询权限表中是否有相同的菜单
        Permission permissionByName = permissionMapper.selectPermissionByName(permission.getName());
        //如果查询的结果不为空则存在同名菜单, 抛出新增失败异常
        if (!ObjectUtils.isEmpty(permissionByName)) {
            throw new RuntimeException("当前新增的菜单名称在数据库中有与之同名的菜单项! 新增失败!");
        }

        //插入新菜单信息
        return super.insert(permission);
    }

    /**
     * @Description 跟新权限菜单信息, 跟新前需要判断表中是否重名权限名称和code值
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param permission
     * @return int
     */
    @Override
    public int update(Permission permission) {

        //1. 根据name查询表中是否存在同名的权限菜单信息
        Permission permissionByName = permissionMapper.selectPermissionByName(permission.getName());
        //不为空且查询出的id不等于自己则存在相同的菜单
        if (!ObjectUtils.isEmpty(permissionByName) && !permissionByName.getId().equals(permission.getId())) {
            throw new RuntimeException("修改的菜单权限名称在表中有相同的名称! 修改失败!");
        }

        //如果查询到的信息不存在或者修改的权限菜单不存在表中则进行修改
        //根据id修改
        return super.update(permission);
    }

    /**
     * @Description 根据permissionid删除对应的权限菜单信息, permissionId就是子菜单的parentId查询对应的子菜单列表
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param permissionId
     * @return int
     */
    @Override
    public int deleteById(Long permissionId) {

        //查询permissionId对应的菜单信息是否还有子菜单,  permissionId为参数插parentId不为空则存在子菜单
        /*
        2,0,权限管理
        3,2,用户管理
         */
        List<Permission> permissionSubList = permissionMapper.selectPermissionListByParentId(permissionId);
        //如果查询到的子菜单不为空则还有子菜单不允许删除
        if (!ObjectUtils.isEmpty(permissionSubList)) {
            throw new RuntimeException("当前删除的父级菜单含有子菜单! 不允许删除!");
        }

        //没有子菜单则可以删除父级菜单
        return super.deleteById(permissionId);
    }

    /**
     * @Description 根据用户Id查询权限编码code
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param userId
     * @return List<String>
     */
    @Override
    public List<String> selectPermissionCodeListByUserId(Long userId) {
        List<Permission> permissionList = null;

        //如果是管理员用户则全部查询出来
        if (userId == 1) {
            //管理员查询全部的权限菜单集合
            permissionList = permissionMapper.findAll();
        } else {
            //1. 普通用户就根据用户id查询出所有的权限信息
            permissionList = permissionMapper.selectPermissionListByAdminId(userId);
        }

        //2. 把查询出的权限信息过滤, 我们只要权限值code
        List<String> permissionCodeList = permissionList.stream()
                .filter(permission -> permission.getType() == 2)
                .map(Permission::getCode)
                .collect(Collectors.toList());

        //把过滤收集的结果返回
        return permissionCodeList;
    }
}
