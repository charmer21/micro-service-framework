package com.companyname;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 应用入口
 */
@SpringBootApplication
@Import(value = {com.companyname.framework.WebConfiguration.class, com.companyname.webapp.config.WebConfiguration.class})
public class Application {

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }

}
