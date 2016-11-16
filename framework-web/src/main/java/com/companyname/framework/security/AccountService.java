package com.companyname.framework.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 账户服务（包含账户、角色、权限的读取）
 */
@Service
public class AccountService {

    @Autowired
    private AccountMapper mapper;

    /**
     * 根据登录账号获取用户信息
     * @param loginid 登录账号
     * @return
     */
    public Account findByLoginId(String loginid){
        return mapper.findByLoginId(loginid);
    }

    /**
     * 根据登录账号获取该用户的所有角色
     * @param loginid 登录账号
     * @return
     */
    public List<Role> getRoles(String loginid){
        return mapper.getRoles(loginid);
    }

    /**
     * 判断登录账号是否拥有权限
     * @param loginId 登录账号
     * @param target
     * @param permissionCode 权限编码
     * @return
     */
    public boolean authorized(String loginId, String target, String permissionCode){
        return mapper.hasPermission(loginId, permissionCode);
    }

}
