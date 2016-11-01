package io.wang.module.mybatis;

import io.wang.Application;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "dev")
@Transactional
public class ApplicationTests {

    @Autowired
    private DemoMapper mapper;

    @Test
    @Rollback
    public void findAll() throws Exception{
        List<Demo> demos =  mapper.findAll();
        Assert.assertEquals(2, demos.size());

        Demo newDemo = new Demo();
        newDemo.setId(UUID.randomUUID().toString().replace("-", ""));
        newDemo.setName("李四");
        newDemo.setMobile("13800138002");
        mapper.save(newDemo);

        demos = mapper.findAll();
        Assert.assertEquals(3, demos.size());
    }
}
