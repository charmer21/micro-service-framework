package com.companyname.framework.security;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AccountMapper {

    /**
     * 根据登录账号获取用户信息
     * @param loginid 登录账号
     * @return
     */
    Account findByLoginId(String loginid);

    /**
     * 根据登录账号获取该用户的所有角色
     * @param loginid 登录账号
     * @return
     */
    List<Role> getRoles(String loginid);

    /**
     * 判断登录账号是否拥有权限
     * @param loginId 登录账号
     * @param target
     * @param permissionCode 权限编码
     * @return
     */
    boolean hasPermission(String loginid, String permissionCode);
}
