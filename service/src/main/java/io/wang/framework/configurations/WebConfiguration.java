package io.wang.framework.configurations;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.web.servlet.RegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private DruidJdbcConfiguration jdbcConfiguration;

    @Autowired
    private DbConfiguration dbConfiguration;

    @Bean
    public RegistrationBean getDruidStatViewServlet(){
        StatViewServlet servlet = new StatViewServlet();
        ServletRegistrationBean bean = new ServletRegistrationBean();
        bean.setServlet(servlet);
        bean.addUrlMappings("/druid/*");
        bean.setLoadOnStartup(1);
        bean.addInitParameter("resetEnable", "false");
        return bean;
    }

    public DataSource getDruidDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        try {
            dataSource.setUsername(jdbcConfiguration.getUserName());
            dataSource.setPassword(jdbcConfiguration.getPassword());
            dataSource.setFilters(jdbcConfiguration.getFilters());
            dataSource.setMaxActive(jdbcConfiguration.getMaxActive());
            dataSource.setMinIdle(jdbcConfiguration.getMinIdle());
            dataSource.setInitialSize(jdbcConfiguration.getInitialSize());
            dataSource.setMaxWait(jdbcConfiguration.getMaxWait());
            dataSource.setTimeBetweenEvictionRunsMillis(jdbcConfiguration.getTimeBetweenEvictionRunsMillis());
            dataSource.setMinEvictableIdleTimeMillis(jdbcConfiguration.getMinEvictableIdleTimeMillis());
            dataSource.setValidationQuery(jdbcConfiguration.getValidationQuery());
            dataSource.setTestWhileIdle(jdbcConfiguration.getTestWhileIdle());
            dataSource.setTestOnBorrow(jdbcConfiguration.getTestOnBorrow());
            dataSource.setTestOnReturn(jdbcConfiguration.getTestOnReturn());
            dataSource.setPoolPreparedStatements(jdbcConfiguration.getPoolPreparedStatements());
            dataSource.setMaxPoolPreparedStatementPerConnectionSize(jdbcConfiguration.getMaxOpenPreparedStatements());
        }catch(SQLException ex){
            throw new BeanInitializationException("DataSouce初始化失败", ex);
        }
        return dataSource;
    }

    @Bean(destroyMethod = "close")
    @Primary
    public DataSource getDb1DruidDataSource() throws BeanInitializationException{
        DruidDataSource dataSource = (DruidDataSource) getDruidDataSource();
        dataSource.setUrl(dbConfiguration.getDb1Url());
        return dataSource;
    }

    @Bean(destroyMethod = "close")
    public DataSource getDb2DruidDataSource() throws BeanInitializationException{
        DruidDataSource dataSource = (DruidDataSource) getDruidDataSource();
        dataSource.setUrl(dbConfiguration.getDb2Url());
        return dataSource;
    }

}
