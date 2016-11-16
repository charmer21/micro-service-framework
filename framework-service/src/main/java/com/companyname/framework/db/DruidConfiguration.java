package com.companyname.framework.db;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

/**
 * Druid连接池配置类
 * 一般不需要修改
 */
@Data
@Configuration
public class DruidConfiguration extends JdbcConfiguration {

    @Value(value = "${druid.jdbc.filters}")
    private String filters;
    @Value(value = "${druid.jdbc.maxWait}")
    private Integer maxWait;
    @Value(value = "${druid.jdbc.poolPreparedStatements}")
    private Boolean poolPreparedStatements;

    /**
     * Druid数据源配置
     *
     * @return
     */
    public DruidDataSource getDruidDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        try {
            dataSource.setUsername(this.getUserName());
            dataSource.setPassword(this.getPassword());
            dataSource.setFilters(this.getFilters());
            dataSource.setMaxActive(this.getMaxActive());
            dataSource.setMinIdle(this.getMinIdle());
            dataSource.setInitialSize(this.getInitialSize());
            dataSource.setMaxWait(this.getMaxWait());
            dataSource.setTimeBetweenEvictionRunsMillis(this.getTimeBetweenEvictionRunsMillis());
            dataSource.setMinEvictableIdleTimeMillis(this.getMinEvictableIdleTimeMillis());
            dataSource.setValidationQuery(this.getValidationQuery());
            dataSource.setTestWhileIdle(this.getTestWhileIdle());
            dataSource.setTestOnBorrow(this.getTestOnBorrow());
            dataSource.setTestOnReturn(this.getTestOnReturn());
            dataSource.setPoolPreparedStatements(this.getPoolPreparedStatements());
            dataSource.setMaxPoolPreparedStatementPerConnectionSize(this.getMaxOpenPreparedStatements());
        } catch (SQLException ex) {
            throw new BeanInitializationException("DataSouce初始化失败", ex);
        }
        return dataSource;
    }

    @Value(value = "${druid.jdbc.maxOpenPreparedStatements}")
    private Integer maxOpenPreparedStatements;
}
