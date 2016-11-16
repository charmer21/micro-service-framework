package com.companyname.projectname.service;


import com.companyname.framework.MicroService;
import com.companyname.projectname.domain.Demo;
import com.companyname.projectname.domain.DemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Demo2Service extends MicroService {

    @Autowired
    private DemoRepository mapper;

    @Override
    public String getDbKey() {
        return "db2";
    }

    public List<Demo> query(){
        logger.debug("DemoService => query()");
        return mapper.findAll();
    }
}