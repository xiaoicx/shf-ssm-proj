package com.atguigu.interceptor;

import com.alibaba.dubbo.common.serialize.support.json.JsonObjectOutput;
import com.alibaba.fastjson.JSON;
import com.atguigu.entity.UserInfo;
import com.atguigu.result.Result;
import com.atguigu.result.ResultCodeEnum;
import com.atguigu.util.WebUtil;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @className: LoginUrlInterceptor
 * @Package: com.atguigu.interceptor

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
public class LoginUrlInterceptor implements HandlerInterceptor {

    //拦截请求 查看访问的url是否是登录过的用户访问
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("USER_INFO");

        //session中是否有登录的用户
        if (ObjectUtils.isEmpty(userInfo)) {

            //回显内容
            response.getWriter()
                    .write(JSON.toJSONString(Result.build(null, ResultCodeEnum.LOGIN_AUTH.getCode(), ResultCodeEnum.LOGIN_AUTH.getMessage())));

            //拦截请求
            return false;
        }

        return true;
    }
}
