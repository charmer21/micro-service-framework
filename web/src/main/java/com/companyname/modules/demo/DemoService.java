package com.companyname.modules.demo;


import com.companyname.framework.MicroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoService extends MicroService {

    @Autowired
    private DemoMapper mapper;

    public List<Demo> query(){
        logger.debug("DemoService => query()");
        return mapper.findAll();
    }
}
