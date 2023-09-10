package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Role;
import com.atguigu.service.PermissionService;
import com.atguigu.service.RoleAdminService;
import com.atguigu.service.RoleService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @className: RoleController
 * @Package: com.atguigu.controller

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
@Slf4j
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    private static final String PAGE_ROLE = "role/index";
    private static final String PAGE_SUCCESS = "common/successPage";
    private static final String PAGE_EDIT = "role/edit";
    private static final String PAGE_REDIRECT_ROLE = "redirect:/role";

    @Reference
    private RoleService roleService;

    @Reference
    private RoleAdminService roleAdminService;

    @Reference
    private PermissionService permissionService;

    /**
     * @Description 分页查询所有角色
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param
     * @return ModelAndView
     */
    @PreAuthorize("hasAuthority('role.show')")
    @RequestMapping
    public ModelAndView findAll(@RequestParam Map<String, Object> pageParam) {

        log.info("分页查询参数 pageParam={}", pageParam);

        PageInfo<Role> rolePageInfo = roleService.findPage(pageParam);
        log.info("分页查询的结果 pagehelp={}", rolePageInfo);

        ModelAndView mv = new ModelAndView();
        mv.setViewName(PAGE_ROLE);
        mv.addObject("page", rolePageInfo);
        mv.addObject("filters", pageParam);
        return mv;
    }

    /**
     * @Description 保存角色
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param role
     * @return ModelAndView
     */
    @PreAuthorize("hasAuthority('role.create')")
    @PostMapping("/save")
    public ModelAndView saveRole(Role role) {

        if (role != null && Objects.equals(role.getRoleName(), "")) {
            log.info("save页面保存的数据为 role={}", role);
            return successPage("添加失败!");
        }
        int effectRows = roleService.insert(role);
        log.info("save页面保存的数据为 role={} 插入状态={}", role, effectRows);
        return successPage("添加成功! 条数" + effectRows + ".");
    }

    /**
     * @Description 跳转到编辑角色信息页面
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param id
     * @return ModelAndView
     */
    @PreAuthorize("hasAuthority('role.show')")
    @RequestMapping("/edit/{id}")
    public ModelAndView editRole(@PathVariable("id") Long id) {

        log.info("编辑角色的id={}", id);
        ModelAndView mv = new ModelAndView();
        if (Objects.equals(id, "")) {
            mv.setViewName(PAGE_EDIT);
            mv.addObject("messagePage", "跳转失败! 修改的id为空!");
            return mv;
        }
        Role role = roleService.selectById(id);
        mv.setViewName(PAGE_EDIT);
        mv.addObject("role", role);
        return mv;
    }

    /**
     * @Description 修改角色信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param role
     * @return ModelAndView
     */
    @PreAuthorize("hasAuthority('role.edit')")
    @PostMapping("/update")
    public ModelAndView updateRoleById(Role role) {

        log.info("修改的角色信息 role={}", role);
        if (Objects.nonNull(role) && Objects.equals(role.getRoleName(), "")) {
            return successPage("修改失败! 角色名字为空.");
        }

        int effectRows = roleService.update(role);
        return successPage("修改成功! 修改的条数: " + effectRows + "条");
    }

    /**
     * @Description 根据id删除角色
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param id
     * @return ModelAndView
     */
    @PreAuthorize("hasAuthority('role.delete')")
    @RequestMapping("/delete/{id}")
    public ModelAndView deleteRoleById(@PathVariable("id") Long id) {

        int effectRows = roleService.deleteById(id);
        log.info("删除角色的id={} 影响记录数rows={}", id, effectRows);
        ModelAndView mv = new ModelAndView();
        mv.setViewName(PAGE_REDIRECT_ROLE);
        return mv;
    }

    /**
     * @Description 跳转到权限管理页面
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param roleId
     * @return String
     */
    @PreAuthorize("hasAuthority('role.show')")
    @RequestMapping("/assignShow/{roleId}")
    public ModelAndView assignShow(@PathVariable("roleId") Long roleId) {
        log.debug("roleid={}", roleId);

        ModelAndView mv = new ModelAndView();
        mv.addObject("roleId", roleId);
        mv.setViewName("role/assignShow");

        List<Map<String, Object>> permissionIdByRoleId = permissionService.findPermissionIdByRoleId(roleId);
        mv.addObject("zNodes", JSON.toJSONString(permissionIdByRoleId));

        return mv;
    }

    /**
     * @Description 根据角色id保存当前权限信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param roleId
     * @param permissionIds
     * @return String
     */
    @PreAuthorize("hasAuthority('role.assgin')")
    @PostMapping("/assignPermission")
    public ModelAndView saveAssignPermission(@RequestParam("roleId") Long roleId,
                                             @RequestParam("permissionIds") List<Long> permissionIds) {
        log.debug("保存的roleId={} permissionIds={}", roleId, permissionIds);
        permissionService.saveAssignPermission(roleId, permissionIds);
        return successPage("保存权限成功!");
    }
}
