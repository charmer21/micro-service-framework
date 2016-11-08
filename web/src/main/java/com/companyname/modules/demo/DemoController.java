package com.companyname.modules.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "demo")
public class DemoController {

    @Autowired
    private DemoService service;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Demo> query(){
        return service.query();
    }
}
