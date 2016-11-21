package com.companyname.framework.security;

import lombok.Data;

/**
 * 用户角色
 */
@Data
public class Role {

    /**
     * 角色Id
     */
    private String id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色编码
     */
    private String code;
    /**
     * 角色描述
     */
    private String description;
}
