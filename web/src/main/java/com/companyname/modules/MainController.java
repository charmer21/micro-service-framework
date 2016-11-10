package com.companyname.modules;


import com.companyname.framework.MicroController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController extends MicroController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model){
        logger.debug("debug");
        model.addAttribute("userName",  getUser().getUserName());
        return "index";
    }
}
