package com.atguigu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @className: WebSecurityConfig
 * @Package: com.atguigu.config

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder.encode("123456"))
                .roles("");
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //允许iframe嵌套显示
        http.headers()
                .frameOptions()
                .sameOrigin();

        //登录设置
        http.authorizeRequests()

                // 默认允许匿名访问的路径
                .antMatchers("/static/**", "/login").permitAll()  //允许匿名用户访问的路径
                .anyRequest()
                .authenticated()    // 其它页面全部需要验证

                //允许表单登录以及登录表单的提交地址和登录后默认访问的路径
                .and()
                .formLogin()
                .loginPage("/login")    //用户未登录时，访问任何需要权限的资源都转跳到该路径，即登录页面，此时登陆成功后会继续跳转到第一次访问的资源页面（相当于被过滤了一下）
                .defaultSuccessUrl("/") //登录认证成功后默认转跳的路径，意思时admin登录后也跳转到/user

                //允许退出和设置退出表单的提交地址, 已经退出后默认访问的路径
                .and()
                .logout()
                .logoutUrl("/logout")   //退出登陆的路径，指定spring security拦截的注销url,退出功能是security提供的
                .logoutSuccessUrl("/login")//用户退出后要被重定向的url

                //关闭CSRF跨域
                .and()
                .csrf().disable();//关闭跨域请求伪造功能

        //曲线访问异常处理器
        /*http.exceptionHandling()
                .accessDeniedPage("/auth");*/ //指定拒绝的页面url
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
