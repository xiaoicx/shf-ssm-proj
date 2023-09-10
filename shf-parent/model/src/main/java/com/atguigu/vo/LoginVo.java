package com.atguigu.vo;


import lombok.Data;
import lombok.ToString;

/**
 * 登录对象
 */
@Data
@ToString
public class LoginVo {

    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

}
