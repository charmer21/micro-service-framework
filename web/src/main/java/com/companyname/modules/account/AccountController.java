package com.companyname.modules.account;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AccountController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginView(Model model){
//        String password = BCrypt.hashpw("pass@word1", BCrypt.gensalt());
//        model.addAttribute("password", password);
        return "login";
    }

    @RequestMapping(value = "accessDenied", method = RequestMethod.GET)
    public String accessDenied(Model model){
        return "error/accessDenied";
    }

}
