package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.entity.Permission;
import com.atguigu.entity.Role;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import com.atguigu.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @className: IndexController
 * @Package: com.atguigu.controller

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
@Slf4j
@Controller
public class IndexController {

    @Reference
    private AdminService adminService;

    @Reference
    private RoleService roleService;

    @Reference
    private PermissionService permissionService;

    private static final String PAGE_INDEX = "frame/index";

    /**
     * @Description 后台主页动态菜单
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param
     * @return ModelAndView
     */
    @RequestMapping("/")
    public ModelAndView indexPage() {

        //0.0  使用了SpringSecurity框架登录后需要动态的获取登录的用户名
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();//getPrincipal()返回了身份信息
        String userName = null;
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }

        //根据用户名获取登录的用户信息
        Admin adminByUserName = new Admin();
        adminByUserName.setUsername(userName);
        Admin selectByUserName = adminService.selectByUserName(adminByUserName);

        //还未登陆所以固定一个用户
        //Long adminId = 1L;

        ModelAndView mv = new ModelAndView();
        mv.setViewName(PAGE_INDEX);

        //1. 查询当前登录用户的信息
        Admin admin = adminService.selectById(selectByUserName.getId());
        mv.addObject("admin", admin);

        //2. 查询当前用户的角色信息, 根据adminId查询
        List<Role> roleList = roleService.selectRoleByAdminId(selectByUserName.getId());
        mv.addObject("rolelist", roleList);

        //3. 查询出当前登录用的菜单列表, 根据用户id查询查询出用的权限列表
        List<Permission> menu = permissionService.selectMenuPermissionListByAdminId(selectByUserName.getId());
        mv.addObject("permissionList", menu);

        return mv;
    }

    /**
     * @Description 返回用户登录的信息 JSON格式
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param
     * @return Object
     */
    @ResponseBody
    @GetMapping("/getInfo")
    public Object getLoginUserInfo() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
