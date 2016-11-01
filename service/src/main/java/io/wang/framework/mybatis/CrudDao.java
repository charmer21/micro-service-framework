package io.wang.framework.mybatis;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class CrudDao<T> {

    protected abstract AbstractDbFactory getDbFactory();

    private SqlSession getSession() {
        SqlSession session = getDbFactory().getSessionFactory().openSession();
        return session;
    }

    public T getMapper(Class clazz) {
        SqlSession session = getSession();
        try {
            return (T) session.getMapper(clazz);
        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }


    public List<T> findAll(String sqlKey) {
        SqlSession session = getSession();
        try {
            return session.selectList(sqlKey);
        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }
}
