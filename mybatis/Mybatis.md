## 1.为什么使用Mabtis,对JDBC的改进

![](.\img\jdbc存在的问题.png)

1、 **数据库链接创建、释放频繁造成系统资源浪费从而影响系统性能**，如果使用数据库链接池可解
决此问题。
2、 **Sql 语句在代码中硬编码**，造成代码不易维护，实际应用 sql 变化的可能较大， sql 变动需要改变
java 代码。
3、 **使用 preparedStatement 向占有位符号传参数存在硬编码**，因为 sql 语句的 where 条件不一定，
可能多也可能少，修改 sql 还要修改代码，系统不易维护。
4、 对结果集解析存在硬编码（查询列名）， sql 变化导致解析代码变化，系统不易维护，如果能将数
据库记录封装成 pojo 对象解析比较方便。  

## 2.如何实现一个Mybatis 框架

2.1 用到的知识点

  工厂模式(Factory )

 构造者模式（Builder）

代理模式

自定义注解

xml 解析

数据库元数据的反射

2.2 实现步骤

1.导入maven坐标

2.工具类导入 ：XmlConfigBuilder 解析XML, Executor 执行SQL

3.创建SqlMapConfig.xml配置文件

4.创建用于保存sql的类Mapper 包含字段：

 String 的querysql ,String 类型的resultType 全限定类名

5.Configuration类拥有保存SqlMapCOnfig.xml 的 username,password,url,driver

6.创建数据表User类

7.创建自定义注解@Select 

8.使用@Select 注解开发UserDao接口

2.3 基于注解方式定义Mybatis框架

 2.3.1 工厂模式

自定义SqlSession 接口创建一个动态代理对象getMapper。

selectList（）用于传统方式生成。

2.3.2 **定义DefaultSqlSession用于实现SqlSession**:

- 封装Configuration 封装mybatis 配置文件。

-  Executor SQL 的执行器
- 实现getMaper()方法生成mapper的代理对象 
  1. 创建invocationHandler
  2. 创建动态代理对象

-  selectList 实现查询列表 

   1.通过id 获得Mapper 对象

  2.通过mapper活儿sql语句和返回值类型

3. 通过SQL 语句的Eexutor 执行器来执行sql语句

**2.3.3使用代理模式编写 MapperProxyFactory 类**  

采用动态代理实现，

```
-->1.SqlSessionFactoryBuilder
--->2.xmlConfigBuilder  解析SqlMapConfig.xml字节流保存到Congiguration
---->3.Configuration parse(InputStream inputStream) 方法创建一个解析器SAXReader 
----->4.读取xml,得到Document
----->5.获得数据库信息保存在configuration 对象中

----->通过getMappers(String resource,Configuration configuration) 
读取： //封装每个映射配置文件里的每个statement的信息：SQL语句，结果集封装类型
            List<Node> nodes = document.selectNodes("//mappers/mapper");

  <mappers>
        <mapper resource="com/zengjx/dao/UserDao.xml"/>
    </mappers>

 String  resource=com/zengjx/dao/UserDao.xml
--->对每一个映射配置文件进行解析：
--->对com/zengjx/dao/UserDao.xml 进行解析
获得 映射器的全限定名 “namespace” ,
    "id"
   "resultType"
    sql   语句
   String key="com.zengjx.dao.UserDao.queryAll"  方法的全限定名
    value=mapper对象
  封装到Mapper对象mapper 保存在configuration中的mappers （HashMap中）


------>6.将configuration 传入DefaultSqlSessionFactory中
------>获得

------>加载数据库驱动：Class.forName(configuration.getDriver());
----- >//通过驱动管理类获取数据库链接
connection = DriverManager
.getConnection("jdbc:mysql://localhost:3306/mybatis?characterEncoding=utf-8","ro
ot", "root");
------>7. DefaultSqlSessionFactory 的openSession 返回一个session对象
----->8.   UserDao userDao = sqlSession.getMapper(UserDao.class);

---> 映射器的代理对象getMapper(Class<T> mapperInterfaceClazz)
@Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //执行SQL语句，得到结果集，封装结果集，返回封装的结果：需要我们把所有配置信息封装到了Configuration里
                        //需要一个mapper，里边有SQL和resultTyp。key：全限定类名 +  方法名
                        String key = mapperInterfaceClazz.getName() +"." + method.getName();
                        Mapper mapper = configuration.getMappers().get(key);
                        //需要有一个连接对象：我们自己创建，使用完成之后，要关闭连接
                        return Executor.selectList(connection, mapper);
                    }
取出代理：根据getMapper传入的UserDao对象全限定名 mapperInterfaceClazz.getName() +"." + method.getName();，
去---》Executor.selectList(connection, mapper);
----》 public static <E> List<E> selectList(Connection connection, Mapper mapper) 
1.获取Sql 语句String queryString = mapper.getSql();
2.定义PrepareStatement  ps
3. 执行查询 ps = connection.prepareStatement(queryString);
4.rs = ps.executeQuery();
5.通过Converter.list(rs,Classs.forname) 将结果集保存在list
```

## 3.Mybaits 案例

3.1 pom.xml 导入包

```
<dependency>
<groupId>org.mybatis</groupId>
<artifactId>mybatis</artifactId>
<version>3.4.5</version>
</dependency>
```

3.2 编写实体类实现序列化User implemnets Serialazable

3.3 编写UserMapper接口

```
package com.zengjx.dao;
import com.zengjx.domain.User;

import java.util.List;

public interface UserDao {
    List<User> queryAll();

    /*User findById(Integer id);*/
}
```

3.4 编写UserMapper.xml

```
<mapper namespace="com.zengjx.dao.UserDao">

    <!--
    mybatis里：把给映射器里每个方法的配置，称为一个statement

    delete标签： 要给一个删除的方法进行配置
    update标签： 要给一个修改的方法进行配置
    insert标签： 要给一个添加的方法进行配置
    select标签： 要给一个查询的方法进行配置
        id：方法名
        resultType：结果集的数据要封装成什么对象，写全限定类名
    -->
    <select id="queryAll" resultType="com.zengjx.domain.User">
        select * from user
    </select>
</mapper>
```

3.5 编写SqlMapping.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">

<configuration>
    <!--environments：数据库环境配置，default：使用哪个数据库环境作为默认的数据库-->
    <environments default="self_mybaits">
        <!--environment：配置一个数据库环境信息-->
        <environment id="self_mybaits">
            <!--transactionManager：事务管理方式，固定值JDBC-->
            <transactionManager type="JDBC"/>
            <!--dataSource：数据源。type：数据源的类型，值有三种：UNPOOLED，POOLED，JNDI -->
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql:///mybatis"/>
                <property name="username" value="root"/>
                <property name="password" value="root"/>
            </dataSource>
        </environment>
    </environments>

    <mappers>
        <mapper resource="com/zengjx/dao/UserDao.xml"/>
    </mappers>
</configuration>
```

## SqlMapConfig.xml核心配置文件

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <typeAliases>
        <package name="com.itheima.domain"/>
    </typeAliases>
    <environments default="mysql">
        <environment id="mysql">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql:///mybatis"/>
                <property name="username" value="root"/>
                <property name="password" value="root"/>
            </dataSource>
        </environment>
    </environments>

    <mappers>
        <mapper resource="com/itheima/dao/UserDao.xml"/>
    </mappers>
</configuration>
```

```
properties（属性） ★
settings（全局配置参数） 
typeAliases（类型别名） ★ 
typeHandlers（类型处理器） 
objectFactory（对象工厂） 
plugins（插件） 
environments（环境集合属性对象） 
environment（环境子属性对象） 
transactionManager（事务管理） 
dataSource（数据源） 
mappers（映射器） ★
```

properties属性

```
<configuration>
    <!-- 
 	properties标签：
		resource属性：properties资源文件的路径，从类加载路径下开始查找
		url属性：url地址
	-->
    <properties resource="jdbc.properties"/>

    <environments default="mysql_mybatis">
        <environment id="mysql_mybatis">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <!-- 使用${OGNL}表达式，获取从properties中得到的配置信息 -->
                <property name="driver" value="${jdbc.driver}"/>
                <property name="url" value="${jdbc.url}"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>
    </environments>

    <mappers>
        <mapper resource="com/itheima/dao/UserDao.xml"/>
    </mappers>
</configuration>
```

#### properties注意加载顺序

假如：

有jdbc.properties配置文件在resource下

```properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql:///mybatis49
jdbc.username=root
jdbc.password=root
```

SqlMapConfig.xml配置如下：

```xml
<properties resurce="jdbc.properties">
	<property name="jdbc.username" value="root"/>
    <property name="jdbc.password" value="123456"/>
</properties>
```

那么：

​	Mybatis会首先加载xml配置文件里的的property标签，得到了jdbc.username和jdbc.password

​	然后再加载外部jdbc.properties配置文件，覆盖掉刚刚得到的jdbc.username和jdbc.password的值

总结：

​	后加载覆盖先加载，外部properties配置文件的优先级，要高于xml里property标签的配置

## 4.mabits的数据源的种类

```
<dataSource type="POOLED">
    <property name="driver" value="com.mysql.jdbc.Driver"/>
    <property name="url" value="jdbc:mysql:///mybatis49"/>
    <property name="username" value="root"/>
    <property name="password" value="root"/>
</dataSource>
```



##### 三种dataSouce介绍

- **UNPOOLED**：不使用连接池技术的数据源

  对应Mybatis的`UnpooledDataSouce`类，虽然也实现了`javax.sql.DataSource`接口，但是它的`getConnection()`方法中，并没有真正的使用连接池技术，而是直接从数据库中创建的连接对象，即：`DriverManager.getConnection()`方法

- **POOLED**：使用连接池技术的数据源

  对应Mybatis的`PooledDataSouce`类，它实现了`javax.sql.DataSouce`接口，同时采用了Mybatis自己开发的连接池技术，是我们使用最多的一种数据源

- **JNDI**：使用JNDI技术的数据源

  采用服务器软件提供的JNDI技术实现，从服务器软件中获取数据源。从不同服务器软件中得到的`DataSouce`对象不同。例如：Tomcat中配置了数据连接信息，我们的web应用部署到Tomcat里，就可以获取Tomcat中配置的数据源，而不需要web应用自己再配置数据库连接信息。

## 5.Mybatis 的事务管理

因为Mybatis的是对JDBC的封装，所以Mybatis在本质上也是基于`Connection`对象实现的事务管理，只是把管理的代码封装起来了，是使用`SqlSession`对象进行事务管理的。

##### 5.1 默认事务管理方式

　　默认情况下，我们使用工厂对象的`openSession()`方法得到的`SqlSession`对象，是关闭了事务自动提交的，即：**默认情况下，`SqlSession`是开启了事务的，需要手动提交**。

​	    获取session对象：`factory.openSession()`

　　操作完数据库之后，需要手动提交事务：`sqlSession.commit();`

　　如果要回滚事务，就使用方法：`sqlSession.rollback();`	

##### 5.2 自动提交事务实现

　　Mybatis也支持自动提交事务，操作方法如下：

1. 获取SqlSession对象：`factory.openSession(true)`
2. 操作数据库，事务会自动提交





## 6.动态SQL

常用的标签有：

- `<if></if>`：用来进行判断，相当于Java里的if判断
- `<where></where>`：通常和if配合，用来代替SQL语句中的`where 1=1`
- `<foreach></foreach>`：用来遍历一个集合，把集合里的内容拼接到SQL语句中。例如拼接：`in (value1, value2, ...)`
- `<sql></sql>`：用于定义sql片段，达到重复使用的目的

**if**

```
<if test="使用OGNL表达式进行判断">
	如果为true，这里的内容会拼接SQL语句
</if>
```



```
<select id="search" parameterType="user" resultType="user">
        select * from user where 1=1
        <if test="username != null">
            and username like #{username}
        </if>
        <if test="sex != null">
            and sex = #{sex}
        </if>
    </select>
```

注意：



1. SQL语句中   where 1=1 不能省略
2. 在if标签的test属性中，直接写OGNL表达式，从parameterType中取值进行判断，不需要加#{}或者${}

**where标签**

```
<select id="search" parameterType="user" resultType="user">
        select * from user
        <where>
            <if test="username != null">
                and username like #{username}
            </if>
            <if test="sex != null">
                and sex = #{sex}
            </if>
        </where>
    </select>
```

`<foreach>`标签

```
public class QueryVO {
    
    private Integer[] ids;

    public Integer[] getIds() {
        return ids;
    }

    public void setIds(Integer[] ids) {
        this.ids = ids;
    }
}
```



```
<!--
foreach标签：
	属性：
		collection：被循环遍历的对象，使用OGNL表达式获取，注意不要加#{}
		open：循环之前，拼接的SQL语句的开始部分
		item：定义变量名，代表被循环遍历中每个元素，生成的变量名
		separator：分隔符
		close：循环之后，拼接SQL语句的结束部分
	标签体：
		使用#{OGNL}表达式，获取到被循环遍历对象中的每个元素
-->
<foreach collection="" open="id in(" item="id" separator="," close=")">
    #{id}
</foreach>
id in(41,42,44,45)
```



```
<foreach collection="OGNL获取被循环的对象" open="sql开始部分" item="变量名" separator="分隔符" close="sql结束部分">
    #{变量名}
</foreach>

<foreach collection="ids" open="id in(" item="id" separator="," close=")">
	#{id}
</foreach>
```

<sql> 片段标签

```
<sql id="唯一标识">sql语句片段</sql>

<include refid="sql片段的id"></include>
```



```
 <sql id="selectUser">
        select * from user
    </sql>
```

```
<select id="findByIds" resultType="user" parameterType="queryvo">
    <!-- refid属性：要引用的sql片段的id -->
    <include refid="selectUser"></include> 
    <where>
        <foreach collection="ids" open="and id in (" item="id" separator="," close=")">
            #{id}
        </foreach>
    </where>
</select>
```

