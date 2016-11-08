package com.companyname.framework.security;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AccountMapper {

    Account findByLoginId(String loginid);

    List<Role> getRoles(String loginid);

    boolean hasPermission(String loginid, String permissionCode);
}
