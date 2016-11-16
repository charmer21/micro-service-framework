package com.companyname.projectname.web;


import com.companyname.projectname.domain.Demo;
import com.companyname.projectname.service.Demo2Service;
import com.companyname.projectname.service.DemoService;
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

    @Autowired
    private Demo2Service service2;

    @RequestMapping(value = "/db1", method = RequestMethod.GET)
    public List<Demo> queryFromDb1(){
        return service.query();
    }

    @RequestMapping(value = "/db2", method = RequestMethod.GET)
    public List<Demo> queryFromDb2(){
        return service2.query();
    }
}
