package com.companyname.modules.account;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Controller
public class AccountController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginView(){
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView doLogin(){
        return new ModelAndView("/index.jsp");
    }
}
