package com.atguigu.vo;


import lombok.Data;
import lombok.ToString;

/**
 * 注册对象
 */
@Data
@ToString
public class RegisterVo {

    // 昵称
    private String nickName;

    // 手机号
    private String phone;

    // 密码
    private String password;

    // 验证码
    private String code;
}
