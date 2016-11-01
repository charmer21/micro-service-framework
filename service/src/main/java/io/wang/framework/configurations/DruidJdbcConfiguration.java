package io.wang.framework.configurations;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class DruidJdbcConfiguration extends JdbcConfiguration {

    @Value(value = "${druid.jdbc.filters}")
    private String filters;
    @Value(value = "${druid.jdbc.maxWait}")
    private Integer maxWait;
    @Value(value = "${druid.jdbc.poolPreparedStatements}")
    private Boolean poolPreparedStatements;
    @Value(value = "${druid.jdbc.maxOpenPreparedStatements}")
    private Integer maxOpenPreparedStatements;
}
