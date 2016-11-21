package com.companyname.framework.security;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户账号信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    final String STATUS_ENABLED = "enabled";

    /**
     * 用户id
     */
    private String id;
    /**
     * 登录账号
     */
    private String loginId;
    /**
     * 用户类型
     */
    private String type;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 用户名
     */
    private String name;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 移动电话
     */
    private String mobilePhone;
    /**
     * im
     */
    private String im;
    /**
     * 电话号码
     */
    private String telephone;
    /**
     * 性别
     */
    private String sex;
    /**
     * 出生日期
     */
    private Date birthday;
    /**
     * 状态
     */
    private String status;
    /**
     * 所属组织id
     */
    private String orgId;
    /**
     * 当前用户是否为有效状态
     * @return
     */
    public boolean enabled() {
        return status.equals(STATUS_ENABLED);
    }
}
