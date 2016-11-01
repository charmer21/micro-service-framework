package io.wang.module.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DemoMapper {

    List<Demo> findAll();

    void save(Demo demo);
}
