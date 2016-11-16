package com.companyname.projectname.service;


import com.companyname.projectname.domain.Demo;
import com.companyname.projectname.domain.DemoRepository;
import com.companyname.framework.MicroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoService extends MicroService {

    @Autowired
    private DemoRepository mapper;

    public List<Demo> query(){
        logger.debug("DemoService => query()");
        return mapper.findAll();
    }
}
