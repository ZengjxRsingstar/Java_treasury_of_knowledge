package com.zengjx.mybatis.defaults;

import com.zengjx.mybatis.SqlSession;
import com.zengjx.mybatis.cfg.Configuration;
import com.zengjx.mybatis.cfg.Mapper;
import com.zengjx.mybatis.utils.Executor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;//配置
    private Connection connection;//连接
    public DefaultSqlSession(Configuration configuration) {
        try {
            this.configuration = configuration;
            Class.forName(configuration.getDriver());//加载数据库驱动
           //获得连接
            this.connection = DriverManager.getConnection(configuration.getUrl(), configuration.getUsername(), configuration.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取映射器的代理对象
     * @param mapperInterfaceClazz 映射器接口的字节码对象Class对象
     *
     *                             类名.class.getClassLoader()
     *                             字节码对象.getClassLoader()
     *
     *                             目标对象.getClass().getInterfaces()-=> Class[]{接口的字节码对象}
     *
     *                             Proxy.newProxyInstance(classLoader, Class[]{接口的字节码对象, 接口的字节码对象,..})
     * @param <T>
     * @return  映射器的代理对象
     */
    @Override
    public <T> T getMapper(Class<T> mapperInterfaceClazz) {
        return (T) Proxy.newProxyInstance(
                mapperInterfaceClazz.getClassLoader(),
                new Class[]{mapperInterfaceClazz},
                new InvocationHandler() {
                    /**
                     * 写代理对象的行为
                     * @param proxy
                     * @param method
                     * @param args
                     * @return
                     * @throws Throwable
                     */
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //执行SQL语句，得到结果集，封装结果集，返回封装的结果：需要我们把所有配置信息封装到了Configuration里
                        //需要一个mapper，里边有SQL和resultTyp。key：全限定类名 +  方法名
                        String key = mapperInterfaceClazz.getName() +"." + method.getName();
                        Mapper mapper = configuration.getMappers().get(key);
                        //需要有一个连接对象：我们自己创建，使用完成之后，要关闭连接
                        return Executor.selectList(connection, mapper);
                    }
                }
        );
    }

    @Override
    public void close() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
