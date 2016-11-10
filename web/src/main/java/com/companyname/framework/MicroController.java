package com.companyname.framework;

import com.companyname.framework.security.Account;
import com.companyname.framework.security.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 * Controller基础类
 */
public abstract class MicroController {

    @Autowired
    private AccountService accountService;

    protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * 获取当前登录用户
     * @return 当前登录用户
     */
    protected Account getUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null) {
            throw new NullPointerException("用户未登录");
        }
        Account account = accountService.findByLoginId(user.getUsername());
        if (account == null) {
            throw new NullPointerException("用户 " + user.getUsername() + " 不存在");
        }
        account.setPassword(null);
        return account;
    }
}
