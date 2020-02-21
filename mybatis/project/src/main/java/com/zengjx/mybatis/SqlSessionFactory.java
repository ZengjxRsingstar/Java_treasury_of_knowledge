package com.zengjx.mybatis;

import com.zengjx.mybatis.cfg.Configuration;
import com.zengjx.mybatis.defaults.DefaultSqlSession;

/**
 * @ClassName SqlSessionFactory
 * @Description TODO
 * @Author zengjx
 * @Company zengjx
 * @Date 2020/2/18  17:17
 * @Version V1.0
 * function： SqlSessionFactory
 * SqlSessionFactory 类进一步通过 openSession()方法，包装了 SqlSession 的实现创建过程。
 * 1.工厂模式
 * 2.获得SqlSession
 */
public interface SqlSessionFactory {

  //获得SqlSession
  public  SqlSession  openSession();
}
