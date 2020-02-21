package com.itheima.mybatis.defaults;

import com.zengjx.mybatis.SqlSession;
import com.zengjx.mybatis.SqlSessionFactory;
import com.zengjx.mybatis.cfg.Configuration;
import com.zengjx.mybatis.defaults.DefaultSqlSession;

public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * 生产一个DefaultSqlSession对象，并且把Configuration对象传递给sqlSession
     * @return
     */
    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
