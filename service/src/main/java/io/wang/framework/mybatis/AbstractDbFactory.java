package io.wang.framework.mybatis;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractDbFactory {

    private static Map<String, SqlSessionFactory> factoryCache = new HashMap<>(5);

    @Autowired
    protected MapperLoader mapperLoader;

    protected abstract String getEnvironment();

    protected abstract DataSource getDataSource();

    public SqlSessionFactory getSessionFactory(){
        String env = getEnvironment();
        if(factoryCache.containsKey(env)){
            return factoryCache.get(env);
        }
        Class[] mappers = mapperLoader.getMapper();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment(env, transactionFactory, getDataSource());
        Configuration configuration = new Configuration(environment);
        for(Class clazz : mappers){
            configuration.addMapper(clazz);
        }
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();

        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(configuration);

        return sessionFactory;
    }
}
