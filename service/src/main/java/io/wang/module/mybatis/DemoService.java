package io.wang.module.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoService {

    @Autowired
    private DemoMapper mapper;

    @Autowired
    private DemoDao dao;

    public List<Demo> query(){
        return dao.getMapper(DemoMapper.class).findAll();
    }
}
