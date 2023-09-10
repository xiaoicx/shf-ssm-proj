package com.atguigu.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: UserDetailServiceImpl
 * @Package: com.atguigu.config

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
@Slf4j
@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Reference
    private AdminService adminService;

    @Reference
    PermissionService permissionService;


    /**
     * @Description SpringSecurity 等候鉴权会吧userName交给UserDetailService处理 我们只需要重写该结构实现loadUserByUserName即可
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param userName
     * @return UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        log.debug("登录的用户名 userName={}", userName);

        //1. 更具userName获取用户信息
        Admin adminByUserName = new Admin();
        adminByUserName.setUsername(userName);
        Admin admin = adminService.selectByUserName(adminByUserName);

        //用户为空表示不存在用户
        if (ObjectUtils.isEmpty(admin)) {
            throw new UsernameNotFoundException("用户名不存在!");
        }

        //设置登录的用户权限有哪些, 根据用户名id查询权限code值, 如果是管理员用户则查询全部的权限值
        List<String> permissionCodeListByUserId = permissionService.selectPermissionCodeListByUserId(admin.getId());

        //创建权限集合
        ArrayList<GrantedAuthority> authorityArrayList = new ArrayList<>();

        //把查询到权限信息封装成GrantedAuthority对象交给springsecurity进行鉴权
        permissionCodeListByUserId.forEach(code -> {

            //将string的code权限值转换为SimpleGrantedAuthority对象
            authorityArrayList.add(new SimpleGrantedAuthority(code));
        });

        //返回用户信息一级权限值集合给spring security处理登录的密码鉴权和权限处理
        return new User(admin.getUsername(), admin.getPassword(), authorityArrayList);
    }


}
