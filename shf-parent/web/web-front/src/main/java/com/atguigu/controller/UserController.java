package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.UserInfo;
import com.atguigu.result.Result;
import com.atguigu.result.ResultCodeEnum;
import com.atguigu.service.UserService;
import com.atguigu.util.MD5;
import com.atguigu.vo.LoginVo;
import com.atguigu.vo.RegisterVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @className: UserController
 * @Package: com.atguigu.controller

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
@Slf4j
@RestController
@RequestMapping("/userInfo")
public class UserController {

    @Reference
    private UserService userService;


    /**
     * @Description 注册用户
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param registerVo
     * @return String
     */
    @PostMapping("/register")
    public Result register(@RequestBody RegisterVo registerVo, HttpSession session) {

        log.debug("注册的信息  registerVo={}", registerVo);

        //验证码错误
        if (!registerVo.getCode().equalsIgnoreCase((String) session.getAttribute("CODE"))) {
            return Result.build(null, ResultCodeEnum.SMS_CODE_ERROR.getCode(), ResultCodeEnum.SMS_CODE_ERROR.getMessage());
        }

        //账号是否存在
        UserInfo userInfo = userService.selectUserByPhone(registerVo.getPhone());
        if (!ObjectUtils.isEmpty(userInfo)) {
            return Result.build(null, ResultCodeEnum.PHONE_REGISTER_ERROR.getCode(), ResultCodeEnum.PHONE_REGISTER_ERROR.getMessage());
        }

        //昵称是否重复
        if (!ObjectUtils.isEmpty(userInfo) && registerVo.getNickName().equalsIgnoreCase(userInfo.getNickName())) {
            return Result.build(null, ResultCodeEnum.NICK_NAME_REPAT.getCode(), ResultCodeEnum.NICK_NAME_REPAT.getMessage());
        }

        //属性对拷赋值
        UserInfo info = new UserInfo();
        registerVo.setPassword(MD5.encrypt(registerVo.getPassword()));
        BeanUtils.copyProperties(registerVo, info);
        info.setStatus(0);

        int rows = userService.insert(info);

        return Result.ok(rows);
    }

    /**
     * @Description 调用api发送验证码
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param phone
     * @param session
     * @return Result
     */
    @GetMapping("/sendCode/{phone}")
    public Result smsSendCode(@PathVariable("phone") String phone, HttpSession session) {

        log.debug("为用户 phone={} 设置验证码", phone);

        //1. 调用api发送验证码
        String code = "0000";
        session.setAttribute("CODE", code);
        return Result.ok();
    }

    /**
     * @Description 用户登录
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param loginVo
     * @param session
     * @return Result
     */
    @RequestMapping("/login")
    public Result login(@RequestBody LoginVo loginVo, HttpSession session) {
        log.debug("登录的用户 loginVo={}", loginVo);

        //加密客户端传过来的密码
        loginVo.setPassword(MD5.encrypt(loginVo.getPassword()));

        UserInfo userInfo = userService.selectUserByPhone(loginVo.getPhone());

        log.debug("根据用户手机查询到的用户信息 userInfo={} loginVo中的pwd={}", userInfo, loginVo.getPassword());

        // 用户不存在或者密码错误
        if (!ObjectUtils.isEmpty(userInfo) && !loginVo.getPassword().equalsIgnoreCase(userInfo.getPassword())) {
            return Result.build(null, ResultCodeEnum.PASSWORD_ERROR.getCode(), ResultCodeEnum.PASSWORD_ERROR.getMessage());
        }

        if (userInfo.getStatus() == 1) {
            return Result.build(null, ResultCodeEnum.ACCOUNT_LOCK_ERROR.getCode(), ResultCodeEnum.ACCOUNT_LOCK_ERROR.getMessage());
        }

        session.setAttribute("USER_INFO", userInfo);

        return Result.ok(userInfo);
    }

    /**
     * @Description 登出
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param session
     * @return Result<Object>
     */
    @GetMapping("/logout")
    public Result<Object> logoutInfo(HttpSession session) {
        session.removeAttribute("USER_INFO");
        session.invalidate();
        return Result.ok();
    }

}
