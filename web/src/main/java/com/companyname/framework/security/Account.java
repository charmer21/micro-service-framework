package com.companyname.framework.security;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户账号信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    /**
     * 登录账号
     */
    private String loginId;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 用户名
     */
    private String userName;
}
