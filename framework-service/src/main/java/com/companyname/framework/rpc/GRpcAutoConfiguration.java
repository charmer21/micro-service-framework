package com.companyname.framework.rpc;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(GrpcServerProperties.class)
public class GrpcAutoConfiguration {

    @Bean
    @ConditionalOnBean(annotation = GrpcService.class)
    public GrpcServerRunner gRpcServerRunner(){
        return new GrpcServerRunner();
    }

}
