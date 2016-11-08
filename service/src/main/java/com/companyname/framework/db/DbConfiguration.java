package com.companyname.framework.db;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class DbConfiguration {

    @Value(value = "${jdbc.db1.url}")
    private String db1Url;

    @Value(value = "${jdbc.db2.url}")
    private String db2Url;
}
