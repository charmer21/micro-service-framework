package com.companyname.projectname.domain;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DemoRepository {

    List<Demo> findAll();

    void save(Demo demo);
}
