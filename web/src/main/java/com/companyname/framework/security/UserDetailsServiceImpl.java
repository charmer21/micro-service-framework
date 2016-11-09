package com.companyname.framework.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String loginid) throws UsernameNotFoundException {
        if(StringUtils.isBlank(loginid)){
            throw new UsernameNotFoundException("用户名不存在");
        }

        Account account = accountService.findByLoginId(loginid);
        if(account == null){
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        accountService.getRoles(account.getLoginId()).forEach(r->authorities.add(new SimpleGrantedAuthority(r.getCode())));

        return new User(loginid, account.getPassword(), true, true, true, true, authorities);
    }
}
