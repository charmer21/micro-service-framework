package com.companyname.projectname.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.companyname.framework.db.DruidConfiguration;
import com.companyname.framework.mybatis.MultipleDataSource;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private DbConfiguration dbConfiguration;

    @Autowired
    private DruidConfiguration druidConfiguration;

    /**
     * 针对不同数据库的数据源配置
     *
     * @return
     * @throws BeanInitializationException
     */
    public DataSource getPrimaryDruidDataSource() throws BeanInitializationException {
        DruidDataSource dataSource = druidConfiguration.getDruidDataSource();
        dataSource.setUrl(dbConfiguration.getPrimaryUrl());
        return dataSource;
    }

    /**
     * 配置多数据源
     *
     * @return
     */
    @Bean
    public MultipleDataSource getMultipleDataSource() {
        MultipleDataSource multipleDataSource = new MultipleDataSource();
        multipleDataSource.setDefaultTargetDataSource(getPrimaryDruidDataSource());
        Map<Object, Object> targetDataSource = new ConcurrentHashMap<Object, Object>();
        // 配置多数据源
        targetDataSource.put("primary", getPrimaryDruidDataSource());
        multipleDataSource.setTargetDataSources(targetDataSource);
        return multipleDataSource;
    }
}
