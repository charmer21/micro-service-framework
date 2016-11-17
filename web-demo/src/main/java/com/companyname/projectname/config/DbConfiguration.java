package com.companyname.projectname.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 多数据库配置
 */
@Data
@Configuration
public class DbConfiguration {

    @Value(value = "${jdbc.primary.url}")
    private String primaryUrl;
}
