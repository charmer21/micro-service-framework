package com.companyname.framework.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class MicroPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private AccountService accountService;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        String loginId = authentication.getName();
        Account account = accountService.findByLoginId(loginId);
        return accountService.authorized(account.getLoginId(), targetDomainObject.toString(), permission.toString());
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}
