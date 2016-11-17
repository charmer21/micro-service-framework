package com.companyname.projectname.web;


import com.companyname.framework.MicroController;
import com.companyname.projectname.domain.Demo;
import com.companyname.projectname.service.DemoClient;
import com.companyname.projectname.service.GreeterClient;
import io.grpc.ManagedChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "demo")
public class DemoController extends MicroController {

    @RequestMapping(value = "/db2", method = RequestMethod.GET)
    public List<Demo> queryFromDb2() throws Exception {
        DemoClient client = new DemoClient("localhost", 6565);
        try {
            List<DemoEntity> entities = client.query(10);
            List<Demo> demos = new ArrayList<>(entities.size());
            for (DemoEntity entity : entities) {
                Demo item = new Demo();
                item.setId(entity.getId());
                item.setName(entity.getName());
                item.setMobile(entity.getMobile());
                demos.add(item);
            }
            return demos;
        } finally {
            client.shutdown();
        }
    }

    @RequestMapping(value = "/say", method = RequestMethod.GET)
    public String sayHello() throws Exception {
        GreeterClient client = new GreeterClient("localhost", 6565);
        try {
            String message = client.greet("Hello");
            return message;
        } finally {
            client.shutdown();
        }
    }
}
