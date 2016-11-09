package com.companyname;

import com.companyname.modules.rpc.HelloWorldServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(value = "com.companyname.*")
public class Application {

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
        final HelloWorldServer server = new HelloWorldServer();
        try {
            server.start();
            server.blockUntilShutdown();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

}
