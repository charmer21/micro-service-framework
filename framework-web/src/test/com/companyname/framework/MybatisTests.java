package com.companyname.framework;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import com.companyname.modules.demo.Demo;
import com.companyname.modules.demo.DemoMapper;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "dev")
@Transactional
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
