package com.zengjx.test;
import com.zengjx.dao.UserDao;
import com.zengjx.domain.User;
import com.zengjx.mybatis.SqlSession;
import com.zengjx.mybatis.SqlSessionFactory;
import com.zengjx.mybatis.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * @ClassName HelloController
 * @Description TODO
 * @Author zengjx
 * @Company zengjx
 * @Date 2020/2/18  17:36
 * @Version V1.0
 */
public class MybatisTest {

 @Test
 public  void  test(){
     InputStream  is= this.getClass().getClassLoader().getResourceAsStream("SqlMapConfig.xml");

     //创建SqlsessionFacotry
     SqlSessionFactory sqlSessionFactory = SqlSessionFactoryBuilder.builder(is);
     SqlSession sqlSession = sqlSessionFactory.openSession();
     //3. 使用SqlSession获取映射器 实现类对象（代理模式，是一个代理对象） Proxy.newProxyInstance(类加载器，接口字节码数组, 代理对象的行为)
     UserDao userDao = sqlSession.getMapper(UserDao.class);
     //让代理对象去操作数据库，需要的信息，都在Configuration对象里：写代理对象的行为，操作数据库（调用Executor.selectList方法）
     List<User> userList = (List<User>) userDao.queryAll();
     System.out.println(userList);

     sqlSession.close();
 }


}
