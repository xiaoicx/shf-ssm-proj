package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Admin;
import com.atguigu.entity.AdminRole;
import com.atguigu.entity.Role;
import com.atguigu.service.AdminService;
import com.atguigu.service.RoleAdminService;
import com.atguigu.service.RoleService;
import com.atguigu.util.QiniuUtils;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @className: AdminController
 * @Package: com.atguigu.controller

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    private static final String PAGE_ADMIN_INDEX = "admin/index";
    private static final String PAGE_REDIRECT_ADMIN = "redirect:/admin";
    private static final String PAGE_ADMIN_EDIT = "admin/edit";
    private static final String PAGE_UPLOAD = "admin/upload";

    @Reference
    private AdminService adminService;

    @Reference
    private RoleAdminService roleAdminService;

    @Reference
    private RoleService roleService;

    //注入SpringSecurity框架的加密器
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * @Description 分页模糊查询全部信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param filterMap
     * @return ModelAndView
     */
    @PreAuthorize("hasAuthority('house.show')")
    @RequestMapping
    public ModelAndView findAll(@RequestParam Map<String, Object> filterMap) {

        log.info("分页查询所有管理员信息 filterMap={}", filterMap);

        ModelAndView mv = new ModelAndView();
        mv.setViewName(PAGE_ADMIN_INDEX);

        PageInfo<Admin> adminPageInfo = adminService.findPage(filterMap);

        log.info("分页查询的结果 adminPageInfo={}", adminPageInfo);

        mv.addObject("page", adminPageInfo);
        mv.addObject("filters", filterMap);

        return mv;
    }

    /**
     * @Description 保存用户信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param admin
     * @return ModelAndView
     */
    @PreAuthorize("hasAuthority('admin.create')")
    @PostMapping("/save")
    public ModelAndView saveUser(Admin admin) {

        log.info("新增用户信息  User={}", admin);

        if (!(Objects.nonNull(admin) && Objects.nonNull(admin.getUsername()) && Objects.nonNull(admin.getPassword()))) {
            return successPage("用户添加失败! 用户名或密码为空!");
        }

        //查询当前添加的用户是否存在
        Admin selectByUserName = adminService.selectByUserName(admin);
        if ((Objects.nonNull(selectByUserName) && Objects.nonNull(selectByUserName.getId()) && selectByUserName.getId() != 0)) {
            return successPage("用户添加失败! 用户:" + admin.getUsername() + "已存在!");
        }

        //对密码进行加密, 因为使用了Spring Security需要采用框架的加密方式
        //todo 使用SpringSecurity需要对保存的密码进行加密处理
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        //添加
        int effectRows = adminService.insert(admin);
        if (effectRows > 0) {
            return successPage("用户添加成功! 新增: " + effectRows + "条");
        }
        return successPage("用户添加失败!");
    }

    /**
     * @Description 跳转到修改页面
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param id
     * @return ModelAndView
     */
    @PreAuthorize("hasAuthority('admin.edit')")
    @RequestMapping("/edit/{id}")
    public ModelAndView editUser(@PathVariable("id") Long id) {
        log.info("修改的用户 id={}", id);

        Admin admin = adminService.selectById(id);

        log.info("更具id查询到的用户为 User={}", admin);

        ModelAndView mv = new ModelAndView();
        mv.setViewName(PAGE_ADMIN_EDIT);
        mv.addObject("admin", admin);

        return mv;
    }

    /**
     * @Description 根据id修改用户信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param admin
     * @return ModelAndView
     */
    @PreAuthorize("hasAuthority('admin.edit')")
    @PostMapping("/update")
    public ModelAndView updateUser(Admin admin) {

        log.info("修改的用户信息为 admin={}", admin);
        int effectRows = adminService.update(admin);

        if (effectRows > 0) {
            return successPage("修改成功! 修改: " + effectRows + "条!");
        }
        return successPage("修改失败!");
    }

    /**
     * @Description 根据id删除信息, 逻辑删除
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param id
     * @return ModelAndView
     */
    @PreAuthorize("hasAuthority('admin.delete')")
    @RequestMapping("/delete/{id}")
    public ModelAndView deleteUserById(@PathVariable("id") Long id) {

        log.info("删除的id={}", id);
        int effectRows = adminService.deleteById(id);
        log.info("删除记录数 rows={}", effectRows);

        return new ModelAndView(PAGE_REDIRECT_ADMIN);
    }

    /**
     * @Description 跳转到头像上传页面
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param id
     * @return String
     */
    @PreAuthorize("hasAuthority('role.edit')")
    @RequestMapping("/uploadPage/{id}")
    public ModelAndView uploadPage(@PathVariable("id") Long id) {
        log.debug("用户id={}", id);
        ModelAndView mv = new ModelAndView();
        mv.addObject("id", id);
        mv.setViewName(PAGE_UPLOAD);
        return mv;
    }

    /**
     * @Description 根据id上传头像
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param id
     * @param file
     * @return String
     */
    @PreAuthorize("hasAuthority('role.edit')")
    @PostMapping("/upload/{id}")
    public ModelAndView uploadHeadImage(@PathVariable("id") Long id, @RequestParam("file") MultipartFile file) {
        log.debug("上传头像的id={} 头像文件名file={}", id, file);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String dateName = sdf.format(new Date());

        String FileName = "shf-images/" + dateName + "/" + UUID.randomUUID().toString() + "--" + file.getOriginalFilename();

        String imageUrl;
        try {
            QiniuUtils.upload2Qiniu(file.getBytes(), FileName);
            imageUrl = QiniuUtils.getUrl(FileName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("头像上传失败!");
        }

        //保存头像到用户表
        Admin admin = new Admin();
        admin.setId(id);
        admin.setHeadUrl(imageUrl);
        int rows = adminService.update(admin);

        return successPage("上传成功! 修改数量:" + rows + " 条");
    }


    /**
     * @Description 跳转到角色分配页面
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param adminId
     * @return String
     */
    @PreAuthorize("hasAuthority('admin.assgin')")
    @GetMapping("/assignPage/{id}")
    public ModelAndView assignPage(@PathVariable("id") Long adminId) {

        log.debug("查询的用户id={}", adminId);

        ModelAndView mv = new ModelAndView();
        mv.addObject("adminId", adminId);

        //1. 根据id查询用户已经分配的角色
        List<AdminRole> roleListByAdminId = roleAdminService.selectListByAdminId(adminId);

        //2. 查询所有的角色信息
        List<Role> allRoleList = roleService.findAll();

        //分配的角色信息
        ArrayList<Role> assignRoleList = new ArrayList<>();
        //未分配的角色信息
        ArrayList<Role> unAssignRoleList = new ArrayList<>();

        //adminId从未分配任何角色
        if (ObjectUtils.isEmpty(roleListByAdminId)) {
            mv.addObject("unAssignRoleList", allRoleList);
            mv.addObject("assignRoleList", assignRoleList);
        }

        //把查询到已分配角色的roleId归档
        List<Long> roleListByRoleId = roleListByAdminId.stream()
                .map(AdminRole::getRoleId)
                .collect(Collectors.toList());

        //开始分类收集
        allRoleList.forEach(role -> {

            //如果查询到的角色id包含当前角色id则表示已添加
            if (roleListByRoleId.contains(role.getId())) {
                assignRoleList.add(role);
            } else {

                //查询到的角色id不包含当前角色id表示未添加
                unAssignRoleList.add(role);
            }
        });

        //收集完成
        mv.addObject("unAssignRoleList", unAssignRoleList);
        mv.addObject("assignRoleList", assignRoleList);
        mv.setViewName("admin/assignShow");

        return mv;
    }


    /**
     * @Description 保存分配的角色信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param adminId
     * @param roleIds
     * @return ModelAndView
     */
    @PreAuthorize("hasAuthority('admin.assgin')")
    @PostMapping("/assignRole")
    public ModelAndView saveAssignRole(@RequestParam("adminId") Long adminId, @RequestParam("roleIds") List<Long> roleIds) {

        //adminId=17  roleIds=[29, 28, 27, 26]
        log.debug("adminId={}  roleIds={}", adminId, roleIds);

        roleAdminService.saveAssignRoleList(adminId, roleIds);

        return successPage("添加成功!");
    }


}
