package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Permission;
import com.atguigu.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @className: PermissionController
 * @Package: com.atguigu.controller

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
@Slf4j
@Controller
@RequestMapping("/permission")
public class PermissionController extends BaseController {

    private static final String PAGE_INDEX = "permission/index";
    private static final String PAGE_CREATE = "permission/create";
    private static final String PAGE_EDIT = "permission/edit";
    private static final String PAGE_REDIRECT_HOME = "redirect:/permission";
    @Reference
    private PermissionService permissionService;

    /**
     * @Description 跳转到权限菜单管理页面
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param
     * @return ModelAndView
     */
    @GetMapping
    public ModelAndView permissionIndex() {
        //1. 查询所有权限构建菜单
        List<Permission> permissionAllMenu = permissionService.findAllMenu();
        ModelAndView mv = new ModelAndView();
        mv.addObject("list", permissionAllMenu);
        mv.setViewName(PAGE_INDEX);
        return mv;
    }

    /**
     * @Description 跳转到菜单权限创建页面
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param permission
     * @return ModelAndView
     */
    @GetMapping("/create")
    public ModelAndView createPage(Permission permission) {

        log.debug("跳转到创建页面携带的参数 permission={}", permission);

        ModelAndView mv = new ModelAndView();
        mv.setViewName(PAGE_CREATE);
        mv.addObject("permission", permission);
        return mv;
    }

    /**
     * @Description 保存权限菜单信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param permission
     * @return ModelAndView
     */
    @PostMapping("/save")
    public ModelAndView savePermission(Permission permission) {

        log.debug("保存的权限菜单信息 permission={}", permission);

        //保存的权限菜单信息 permission=
        // Permission(parentId=0, name=订单管理, url=/order, code=null, type=1, sort=1, level=null, children=null, parentName=null)
        int rows = permissionService.insert(permission);

        if (rows > 0) {
            return successPage("保存成功! 新增记录:" + rows + " 条");
        }
        return successPage("保存失败! ");
    }

    /**
     * @Description 根据id跳转到对应的权限菜单的修改页面
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param permissionId
     * @return ModelAndView
     */
    @GetMapping("/edit/{permissionId}")
    public ModelAndView editPage(@PathVariable("permissionId") Long permissionId) {

        log.debug("修改的permissionId={}", permissionId);
        Permission permission = permissionService.selectById(permissionId);
        ModelAndView mv = new ModelAndView();
        mv.setViewName(PAGE_EDIT);
        mv.addObject("permission", permission);
        return mv;
    }

    /**
     * @Description 更新权限菜单信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param permission
     * @return ModelAndView
     */
    @PostMapping("/update")
    public ModelAndView updatePermission(Permission permission) {

        log.debug("跟新的信息 permission={}", permission);

        int rows = permissionService.update(permission);
        if (rows > 0) {
            return successPage("跟新成功! 跟新数量: " + rows + " 条");
        }
        return successPage("跟新失败!");
    }

    /**
     * @Description 根据permissionId删除对应的权限菜单信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param permissionId
     * @return ModelAndView
     */
    @GetMapping("/delete/{permissionId}")
    public String deletePermission(@PathVariable("permissionId") Long permissionId) {

        log.debug("需要删除的permissionId={}", permissionId);

        //根据permissionId删除
        int rows = permissionService.deleteById(permissionId);
        log.debug("删除的记录数rows={}", rows);

        //跳转到菜单管理页面
        return PAGE_REDIRECT_HOME;
    }
}
