package io.wang.framework.configurations;

import io.wang.framework.mybatis.MapperLoader;
import io.wang.module.mybatis.DemoMapper;

import java.util.ArrayList;
import java.util.List;

public class MapperLoaderImpl extends MapperLoader {

    @Override
    public Class[] getMapper() {
        List<Class> mappers = new ArrayList<>();
        mappers.add(DemoMapper.class);
        return mappers.toArray(new Class[mappers.size()]);
    }
}
