package com.companyname;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.companyname.modules.configuration.DbConfiguration;
import com.companyname.framework.db.DruidJdbcConfiguration;
import com.companyname.framework.security.PermissionEvaluatorImpl;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.RegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * SpringBoot Web配置类
 */
@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private DbConfiguration dbConfiguration;

    @Autowired
    private DruidJdbcConfiguration jdbcConfiguration;

    /**
     * Druid监控页面配置
     * @return
     */
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

    /**
     * Druid数据源配置
     * @return
     */
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

    /**
     * 针对不同数据库的数据源配置
     * @return
     * @throws BeanInitializationException
     */
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

    /**
     * 拦截器配置
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LocaleChangeInterceptor());
        registry.addInterceptor(new ThemeChangeInterceptor()).addPathPatterns("/**").excludePathPatterns("/druid/**");
        super.addInterceptors(registry);
    }

    /**
     * SpringSecurity Bean配置
     * @return
     */
    @Bean
    public PermissionEvaluator getPermissionEvaluator(){
        return new PermissionEvaluatorImpl();
    }
    @Bean
    public DefaultMethodSecurityExpressionHandler getExpressionHandler(){
        DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
        handler.setPermissionEvaluator(getPermissionEvaluator());
        return handler;
    }
    @Bean
    public DefaultWebSecurityExpressionHandler getWebExpressionHandler(){
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(getPermissionEvaluator());
        return handler;
    }


}
