package com.companyname.framework.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountMapper mapper;

    public Account findByLoginId(String loginid){
        return mapper.findByLoginId(loginid);
    }

    public List<Role> getRoles(String loginid){
        return mapper.getRoles(loginid);
    }

    public boolean authorized(String loginId, String target, String permission){
        return mapper.hasPermission(loginId, permission);
    }

}
