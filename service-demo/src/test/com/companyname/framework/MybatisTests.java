package com.companyname.framework;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import com.companyname.modules.demo.Demo;
import com.companyname.modules.demo.DemoMapper;

import java.util.List;

/**
 * Created by Wangzr on 03/11/2016.
 */
public class MybatisTests {

    @Autowired
    private DemoMapper mapper;

    @Test
    @Rollback
    public void findAll() throws Exception {
        List<Demo> demos = mapper.findAll();
        Assert.assertEquals(3, demos.size());
    }

//        Demo newDemo = new Demo();
//        newDemo.setId(UUID.randomUUID().toString().replace("-", ""));
//        newDemo.setName("李四");
//        newDemo.setMobile("13800138002");
//        mapper.save(newDemo);
//
//        demos = mapper.findAll();
//        Assert.assertEquals(3, demos.size());
}
