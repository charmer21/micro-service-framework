package com.companyname.modules.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoService {

    @Autowired
    private DemoMapper mapper;

    public List<Demo> query(){
        return mapper.findAll();
    }
}
