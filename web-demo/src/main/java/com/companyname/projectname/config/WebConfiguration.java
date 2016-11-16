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
     * @return
     * @throws BeanInitializationException
     */
    public DataSource getDb1DruidDataSource() throws BeanInitializationException{
        DruidDataSource dataSource = druidConfiguration.getDruidDataSource();
        dataSource.setUrl(dbConfiguration.getDb1Url());
        return dataSource;
    }

    /**
     * 针对不同数据库的数据源配置
     * @return
     * @throws BeanInitializationException
     */
    public DataSource getDb2DruidDataSource() throws BeanInitializationException{
        DruidDataSource dataSource = druidConfiguration.getDruidDataSource();
        dataSource.setUrl(dbConfiguration.getDb2Url());
        return dataSource;
    }

    /**
     * 配置多数据源
     * @return
     */
    @Bean
    public MultipleDataSource getMultipleDataSource(){
        MultipleDataSource multipleDataSource = new MultipleDataSource();
        multipleDataSource.setDefaultTargetDataSource(getDb1DruidDataSource());
        Map<Object, Object> targetDataSource = new ConcurrentHashMap<Object, Object>();
        targetDataSource.put("db1", getDb1DruidDataSource());
        targetDataSource.put("db2", getDb2DruidDataSource());
        multipleDataSource.setTargetDataSources(targetDataSource);
        return multipleDataSource;
    }
}
