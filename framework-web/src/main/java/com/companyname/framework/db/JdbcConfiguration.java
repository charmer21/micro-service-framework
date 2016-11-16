package com.companyname.framework.db;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Jdbc基础配置类
 * 一般不需要修改
 */
@Data
@Configuration
public class JdbcConfiguration {

    @Value(value = "${jdbc.driverClassName}")
    private String driverClassName;
    @Value(value = "${jdbc.username}")
    private String userName;
    @Value(value = "${jdbc.password}")
    private String password;
    @Value(value = "${jdbc.initialSize}")
    private Integer initialSize;
    @Value(value = "${jdbc.maxActive}")
    private Integer maxActive;
    @Value(value = "${jdbc.minIdle}")
    private Integer minIdle;
    @Value(value = "${jdbc.validationQuery}")
    private String validationQuery;
    @Value(value = "${jdbc.timeBetweenEvictionRunsMillis}")
    private Integer timeBetweenEvictionRunsMillis;
    @Value(value = "${jdbc.minEvictableIdleTimeMillis}")
    private Integer minEvictableIdleTimeMillis;
    @Value(value = "${jdbc.testWhileIdle}")
    private Boolean testWhileIdle;
    @Value(value = "${jdbc.testOnBorrow}")
    private Boolean testOnBorrow;
    @Value(value = "${jdbc.testOnReturn}")
    private Boolean testOnReturn;
}
