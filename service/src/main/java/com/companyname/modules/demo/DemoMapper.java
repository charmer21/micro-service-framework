package com.companyname.modules.demo;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DemoMapper {

    List<Demo> findAll();

    void save(Demo demo);
}
