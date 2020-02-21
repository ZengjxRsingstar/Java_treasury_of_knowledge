package com.zengjx.mybatis;

import com.itheima.mybatis.defaults.DefaultSqlSessionFactory;
import com.zengjx.mybatis.cfg.Configuration;
import com.zengjx.mybatis.utils.XMLConfigBuilder;

import javax.xml.bind.annotation.XmlAccessOrder;
import java.io.InputStream;

/**
 * @ClassName HelloController
 * @Description TODO
 * @Author zengjx
 * @Company zengjx
 * @Date 2020/2/18  17:39
 * @Version V1.0
 * 1.构建者模式
 * 2.用来创建SqlSessionFacotry
 * 3.隐藏工厂的构建细节
 *    解析xml配置文件
 *    构建一个Configuration 对象
 */
public class SqlSessionFactoryBuilder {

    //1.解析字节流构建Configuration 对象
    // 2.创建SqlSessionFacotry
     public   static  SqlSessionFactory   builder(InputStream  io){
         XMLConfigBuilder  xmlConfigBuilder =new XMLConfigBuilder();//
         Configuration  configuration=xmlConfigBuilder.parse(io);//
         return  new DefaultSqlSessionFactory(configuration);
     }
}
