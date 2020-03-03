# 1.SpringBoot的自动配置流程

https://github.com/ZhongFuCheng3y/3ySpringBoot自动配置原理

@SpringBootApplication

```
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = {
		@Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
		@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
public @interface SpringBootApplication {
```

```
@SuppressWarnings("deprecation")
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AutoConfigurationPackage
@Import(EnableAutoConfigurationImportSelector.class)
public @interface EnableAutoConfiguration {
```

EnableAutoConfiguration 会自动载入默认配置

`@AutoConfigurationPackage`注解解释成**自动配置包**依靠的是@Import

```
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(AutoConfigurationPackages.Registrar.class)
public @interface AutoConfigurationPackage {

}
```

AutoConfiurationPackage:

```
	@Override
		public void registerBeanDefinitions(AnnotationMetadata metadata,
				BeanDefinitionRegistry registry) {
			register(registry, new PackageImport(metadata).getPackageName());
		}
```

```
@Import(EnableAutoConfigurationImportSelector.class)
public @interface EnableAutoConfiguration {
进入 EnableAutoConfigurationImportSelector
```

进入：List<String> configurations  配置列表

```
AutoConfigurationImportSelector：

@Override
	public String[] selectImports(AnnotationMetadata annotationMetadata) {
		if (!isEnabled(annotationMetadata)) {
			return NO_IMPORTS;
		}
		try {
			AutoConfigurationMetadata autoConfigurationMetadata = AutoConfigurationMetadataLoader
					.loadMetadata(this.beanClassLoader);
			AnnotationAttributes attributes = getAttributes(annotationMetadata);
			List<String> configurations = getCandidateConfigurations(annotationMetadata,
					attributes);
			configurations = removeDuplicates(configurations);
			configurations = sort(configurations, autoConfigurationMetadata);
			Set<String> exclusions = getExclusions(annotationMetadata, attributes);
			checkExcludedClasses(configurations, exclusions);
			configurations.removeAll(exclusions);
			configurations = filter(configurations, autoConfigurationMetadata);
			fireAutoConfigurationImportEvents(configurations, exclusions);
			return configurations.toArray(new String[configurations.size()]);
		}
```

进入：getCandidateConfigurations方法通过SpringFactoriesLoader来加载

```
protected List<String> getCandidateConfigurations(AnnotationMetadata metadata,
      AnnotationAttributes attributes) {
   List<String> configurations = SpringFactoriesLoader.loadFactoryNames(
         getSpringFactoriesLoaderFactoryClass(), getBeanClassLoader());
   Assert.notEmpty(configurations,
         "No auto configuration classes found in META-INF/spring.factories. If you "
               + "are using a custom packaging, make sure that file is correct.");
   return configurations;
}
```

进入loadFacoryNames方法：

```
public static List<String> loadFactoryNames(Class<?> factoryClass, ClassLoader classLoader) {
   String factoryClassName = factoryClass.getName();
   try {
      Enumeration<URL> urls = (classLoader != null ? classLoader.getResources(FACTORIES_RESOURCE_LOCATION) :
            ClassLoader.getSystemResources(FACTORIES_RESOURCE_LOCATION));
      List<String> result = new ArrayList<String>();
      while (urls.hasMoreElements()) {
         URL url = urls.nextElement();
         Properties properties = PropertiesLoaderUtils.loadProperties(new UrlResource(url));
         String factoryClassNames = properties.getProperty(factoryClassName);
         result.addAll(Arrays.asList(StringUtils.commaDelimitedListToStringArray(factoryClassNames)));
      }
      return result;
   }
```

FACTORIES_RESOURCE_LOCATION：

```java
public abstract class SpringFactoriesLoader {

   private static final Log logger = LogFactory.getLog(SpringFactoriesLoader.class);

   /**
    * The location to look for factories.
    * <p>Can be present in multiple JAR files.
    */
   public static final String FACTORIES_RESOURCE_LOCATION = "META-INF/spring.factories";
```

@SpringBootApplication`等同于下面三个注解：

- `@SpringBootConfiguration`
- `@EnableAutoConfiguration`
- `@ComponentScan`

其中`@EnableAutoConfiguration`是关键(启用自动配置)，内部实际上就去加载`META-INF/spring.factories`文件的信息，然后筛选出以`EnableAutoConfiguration`为key的数据，加载到IOC容器中，实现自动配置功能！

# 2.过滤器和拦截器

## 2.1过滤器Filter和拦截器的区别

过滤器可以修改reuquest,而拦截器不乐意。

过滤器需要在servlet容器中实现属于Servelet规范中一部分，拦截器则是独立存能在，拦截器可以适用于Javaee、JavaSE 各种环境。

拦截器可以用**IOC容器中的各种依赖**，而过滤器不能。

过滤器只能在请求前后使用，而拦截器可以详细到每个方法。

过滤器的**执行由Servlet 容器回调完成**，而**拦截器通常通过动态代理方式实现**。

过滤器的声明周期**由servlet容器管理**，而拦截器则可以通过IOC容器来管理，因此可以通过注入方式获取其他Bean的实例，因此使用更方便。

过滤器就是筛选出你要的东西，**比如requeset中你要的那部分**
 **拦截器在做安全方面用的比较多，比如终止一些流程。**

接下来我们举个简单的例子：



1、建立interceptor包，在此包下建立Interceptor类。此处我们以处理Ajax跨域请求为例，关于跨域请求，大家不懂的可以自行学习。代码如下：

```

public class Interceptor implements HandlerInterceptor {
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        // TODO Auto-generated method stub
    }
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {
        // TODO Auto-generated method stub
    }
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {

        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));

        response.setHeader("Access-Control-Allow-Methods", "*");

        response.setHeader("Access-Control-Allow-Credentials", "true");

        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");

        System.err.println("------------------>:已完成跨域处理");

        return true;

    }

}

```

注意此类实现了HandlerInterceptor接口。

2、我们还要注册一下此拦截器才能被springboot检测到，在config包下建立AppConfigurer类，代码如下：

```
@Configuration
public class AppConfigurer extends WebMvcConfigurerAdapter {
    @Bean
    public HandlerInterceptor getMyInterceptor() {
        return new Interceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getMyInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
        

    }

}

```

注意此类继承了WebMvcConfigurerAdapter类。getMyInterceptor方法为拦截器实例注入方法。addInterceptors方法为说明拦截目标方法。此处我们所有请求都拦截。

3、测试结果如下：







![img](https://img-blog.csdn.net/20180719223315245?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM4MDc1NDI1/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)



拦截器配置成功。

过滤器的实现：

### 1.LogCostFilter

https://www.cnblogs.com/paddix/p/8365558.html

1.定义一个LogCqstFilter实现Filter

2.在doFilter中 调用System.currentTimeMills()保存在start;

 3.执行filterChain.doFilter()

4. 再次获取时间减去start

```
public class TestFilter  implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long start = System.currentTimeMillis();
        chain.doFilter(request, response);
        System.out.println("request"+ request);
        System.out.println("reepose"+response);
        System.out.println("过滤器执行时间："+(System.currentTimeMillis()-start));
    }

    @Override
    public void destroy() {
        System.out.println("destroy....");
    }
}

```

 2.在springBoot中配置怎么配置？

在FilterRegistrationBean来完成配置

实例化Filter类，然后指定url的匹配模式，设置过滤器名称和执行顺序

```
@Configuration
public class FilterConfig  {

 @Bean
 public FilterRegistrationBean  filterRegistrationBean(){
     FilterRegistrationBean registrationBean =new FilterRegistrationBean();
     registrationBean.setFilter(new TestFilter());
     registrationBean.addUrlPatterns("/*");
     registrationBean.setOrder(1);//指定执行顺序
     System.out.println("进入过滤器配置类");
     return  registrationBean;

 }
}
```

3.测试 启动后访问任意URL :

```
进入过滤器配置类
       : Completed initialization in 16 ms
过滤器执行时间：77

过滤器执行时间：24
过滤器执行时间：8
2020-02-26 11:12:40.416  INFO 5752 --- [nio-8080-exec-1] c.z.miaosha.controller.LoginController   : goodGoodsVo{miaoshaPrice=0.01, stockCount=72, startDate=Fri Feb 07 03:29:00 CST 2020, endDate=Fri Feb 07 04:00:00 CST 2020}
过滤器执行时间：184
过滤器执行时间：7
destroy...
```

通过注解方式配置：

```
@WebFilter(urlPatterns = "/*",filterName = "TestFilter2")
public class TestFilter2   implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long start = System.currentTimeMillis();
        chain.doFilter(request, response);
        System.out.println("request"+ request);
        System.out.println("reepose"+response);
        System.out.println("TestFilter2过滤器执行时间："+(System.currentTimeMillis()-start));
    }

    @Override
    public void destroy() {
        System.out.println("TestFilter2 destroy....");
    }
}

```

启动类添加过滤器扫描：

```
@SpringBootApplication
@ServletComponentScan("com.zengjx.miaosha.filter")//添加过滤器扫描
public class StartApplcation {

    public static void main(String[] args) {
        SpringApplication.run(StartApplcation.class,args);
    }


```

第二个Filter我们并没有指定执行的顺序，但是却在第一个Filter之前执行。这里需要解释一下，@WebFilter这个注解并没有指定执行顺序的属性，其执行顺序依赖于Filter的名称，是根据Filter类名（注意不是配置的filter的名字）的**字母顺序倒序排列**，**并且@WebFilter指定的过滤器优先级都高于FilterRegistrationBean配置的过滤器。**

```
TestFilter2过滤器执行时间：31
过滤器执行时间：6
requestorg.apache.catalina.connector.RequestFacade@691c286
reeposeorg.apache.catalina.connector.ResponseFacade@4aa881a3
过滤器执行时间：32
requestorg.apache.catalina.connector.RequestFacade@27b277e9
reeposeorg.apache.catalina.connector.ResponseFacade@62b0ed7b
TestFilter2过滤器执行时间：32
requestorg.apache.catalina.connector.RequestFacade@27b277e9
reeposeorg.apache.catalina.connector.ResponseFacade@62b0ed7b
过滤器执行时间：32
```

2. ### 拦截器配置

   1.定义拦截器

   ```
   public class TestInterceptor implements HandlerInterceptor {
       long start = System.currentTimeMillis();
       @Override//请求执行前
       public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
           start = System.currentTimeMillis();
           System.out.println("preHandle....");
           return true;
       }
   
       @Override
       public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
           System.out.println("Interceptor cost="+(System.currentTimeMillis()-start));
           System.out.println("postHandle....");
       }
   
       @Override
       public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
           System.out.println("afterCompletion....");
       }
   }
   ```

   需要实现HandlerInterceptor这个接口，这个接口包括三个方法，preHandle是请求执行前执行的，postHandler是请求结束执行的，但只有preHandle方法返回true的时候才会执行，afterCompletion是视图渲染完成后才执行，同样需要preHandle返回true，该方法通常用于清理资源等工作。除了实现上面的接口外，我们还需对其进行配置：

   这里我们继承了WebMVCConfigurerAdapter，看过前面的文章的朋友应该已经见过这个类了，在进行静态资源目录配置的时候我们用到过这个类。这里我们重写了addInterceptors这个方法，进行拦截器的配置，主要配置项就两个，一个是指定拦截器，第二个是指定拦截的URL。现在我们再启动系统访问任意一个URL：

   #### 定义配置类

```
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter  {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
          registry.addInterceptor(new TestInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
        System.out.println("InterceptorConfig....");
    }
}
```

其实这个实现是有问题的，因为preHandle和postHandle是两个方法，所以我们这里不得不设置一个共享变量start来存储开始值，但是这样就会存在线程安全问题。当然，我们可以通过其他方法来解决，比如通过ThreadLocal就可以很好的解决这个问题，有兴趣的同学可以自己实现。不过通过这一点我们其实可以看到，虽然拦截器在很多场景下优于过滤器，但是在这种场景下，过滤器比拦截器实现起来更简单。

## 3.什么是Springboot？

Spirngboot建立在之前的Spring之上，避免了之前必须做的所有样板代码。可以更少的工作量，更健壮的使用现有的Spring功能。

## 4.SpringBoot 有哪些优点？

优点是相对于传统的SSM、SSH 框架配置繁琐，不同项目有很多重复，模板化配置。

严重降低的开发效率。SpringBoot可以轻松创建基于Spring 的可独立圆心的生产级别的应用。

1.使用JavaConfig有助于避免使用XMl。

2.提供默认配置快速开发。

3.避免大量的Maven导入和版本冲突。

4.没有单独的服务器意味着不需要单独启动tomcat, Glassfish或其他任何东西。

5.**需要更少的配置** 因为没有 web.xml 文件。只需添加用**@ Configuration 注释的类，****然后添加用@Bean 注释的方法，Spring 将自动加载对象并像以前一样对其进行管理。**您甚至可以将@Autowired 添加到 bean 方法中，以使 Spring 自动装入需要的依赖关系中。

6.基于环境的配置 使用这些属性，您可以将您正在使用的环境传递到应用程序：-Dspring.profiles.active = {enviornment}。在加载主应用程序属性文件后，Spring 将在（application{environment} .properties）中加载后续的应用程序属性文件。

## 5.Spring Boot和Spring有什么不同

spring提供了依赖注入、数据绑定、面向切面编程、数据存取但是这些配置复杂。

springboot 可以让这些配置变的简便。

Springboot 的好处：

1.根据classpath 中放入artifacts的自动化配置应用程序。

2.提供非功能特性例如安全和健康检查个给到生产环境。

### 6.如何重新加载Springboot上的修改，而无需重新启动服务器？

这可以使用 DEV 工具来实现。通过这种依赖关系，您可以节省任何更改，嵌入式tomcat 将重新启动。Spring Boot 有一个**开发工具（DevTools）模块**，它有助于提高开发人员的生产力。Java 开发人员面临的一个主要挑战是将文件更改自动部署到服务器并自动重启服务器。开发人员可以重新加载 Spring Boot 上的更改，而无需重新启动服务器。这将消除每次手动部署更改的需要。Spring Boot 在发布它的第一个版本时没有这个功能。这是开发人员最需要的功能。DevTools 模块完全满足开发人员的需求。该模块将在生产环境中被禁用。它还提供 H2 数据库控制台以更好地测试应用程序。

```
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-devtools</artifactId>
<optional>true</optional>
```

### 7.SpringBoot中的监视器是什么？

Spring boot actuator 是 spring 启动框架中的重要功能之一。**Spring boot 监视器可帮助您访问生产环境中正在运行的应用程序的当前状态。有几个指标必须在生产环境中进行检查和监控**。即使一些外部应用程序可能正在使用这些服务来向相关人员触发警报消息。监视器模块公开了一组可直接作为 HTTP URL 访问的REST 端点来检查状态。

## 8.如何自定义端口上运行？

application.properties 中指定端口。server.port = 8090

## 9.怎么使用maven 构建一个SpringBoot 应用？

在pom.xml文件中：声音spring-boot-start-parent

```
<parent>
2     <groupId>org.springframework.boot</groupId>
3     <artifactId>spring-boot-starter-parent</artifactId>
4     <version>2.1.1.RELEASE</version>
5 </parent>
```

我们可以在 Maven central 中找到 *spring-boot-starter-parent* 的最新版本。

使用 starter 父项目依赖很方便，但并非总是可行。例如，如果我们公司都要求项目继承标准 POM，我们就不能依赖 SpringBoot starter 了。

这种情况，我们可以通过对 POM 元素的依赖管理来处理：

```
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>2.1.1.RELEASE</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

## 10.如何事项SpringBoot的安全性？（安全框架）

使用spring-boot-security 添加必要的依赖。

并且添加安全配置配置类将必须扩展WebSecurityConfigurerAdapter 并覆盖其方法。

## 11.如何使用 Spring Boot 实现分页和排序？

 **Spring Data JPA**可以将分页的传给存储库

## 12.Springboot的异常怎么处理？

通过实现**ControllerAdvice**类，处理控制器类抛出的异常。

## 13.如何监视所有SpringBoot微服务？



## 14.什么是Swagger? 你使用 SpringBoot实现了它吗

Swagger广泛用于可视化API，使用Swagger UI为前端人员提供在线沙箱。

Swagger是用于生成RESTful Web 服务的可视化表示的工具，规范和完整的框架实现。它使文档能够以与服务器相同的速度更新。当通过 Swagger 正确定义时，消费者可以使用最少量的实现逻辑来理解远程服务并与其进行交互。因此，Swagger消除了调用服务时的猜测。



## 15.怎么进制某些自动配置特性？

使用@EnableAutoConfiguration 注解，的exclude 属性来指明，例如下面代码：

```
下面的代码段是使 DataSourceAutoConfiguration 无效：

1 // other annotations
2 @EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
3 public class MyConfiguration { }
```

```
如果我们使用 @SpringBootApplication 注解 — 那个将 @EnableAutoConfiguration 作为元注解的项，来启用自动化配置，我们能够使用相同名字的属性来禁用自动化配置：
1 // other annotations
2 @SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
3 public class MyConfiguration { }
我们也能够使用 spring.autoconfigure.exclude 环境属性来禁用自动化配置。application.properties 中的这项配置能够像以前那样做同样的事情：
spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
```



## 16、SpringBoot starter 作用在什么地方？

依赖管理是所有项目中至关重要的一部分。当一个项目变得相当复杂，管理依赖会成为一个噩梦，因为当中涉及太多 artifacts 了。

这时候 SpringBoot starter 就派上用处了。每一个 stater 都在扮演着提供我们所需的 Spring 特性的一站式商店角色。其他所需的依赖以一致的方式注入并且被管理。

所有的 starter 都归于 *org.springframework.boot* 组中，并且它们都以由 *spring-boot-starter-*开头取名。这种命名方式使得我们更容易找到 starter 依赖，特别是当我们使用那些支持通过名字查找依赖的 IDE 当中。

在写这篇文章的时候，已经有超过50个 starter了，其中最常用的是：

- *spring-boot-starter*：核心 starter，包括自动化配置支持，日志以及 YAML
- *spring-boot-starter-aop*：Spring AOP 和 AspectJ 相关的切面编程 starter
- *spring-boot-starter-data-jpa*：使用 Hibernate Spring Data JPA 的 starter
- *spring-boot-starter-jdbc*：使用 HikariCP 连接池 JDBC 的 starter
- *spring-boot-starter-security*：使用 Spring Security 的 starter
- *spring-boot-starter-test*：SpringBoot 测试相关的 starter
- *spring-boot-starter-web*：构建 restful、springMVC 的 web应用程序的 starter

## 17.怎么注册一个定制的自动配置？

https://blog.csdn.net/u014203449/article/details/91890531

### 17.1Spring 的组件注册

https://blog.csdn.net/u014203449/article/details/86559350



为了注册一个自动化配置类，我们必须在 *META-INF/spring.factories* 文件中的*EnableAutoConfiguration* 键下列出它的全限定名：

- spring.factories方式

```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=com.baeldung.autoconfigure.CustomAutoConfiguration
```

如果我们使用 Maven 构建项目，这个文件需要放置在在 package 阶段被写入完成的*resources/META-INF* 目录中。

- 注解方式 

  在notice 项目中启动类添加@Import主角里面指定配置类的class。第一方式用的是notice项目的自动配置会扫描带META-INF 的jar包，但是底层也是用@Import注解。

  ###   

  ### 17.2 当Bean 存在的时候怎么置后执行自动配置？

  为了当 bean 已存在的时候通知自动配置类置后执行，我们可以使

  用@ConditionnalOnMissingBean注解。这个注解的属性：

- value：被检查的 beans 的类型
- name：被检查的 beans 的名字

当将 *@Bean* 修饰到方法时，目标类型默认为方法的返回类型：

```
@Configuration
public class CustomConfigguration{
@Bean
@ConfigtionOnMissingBean
public CustomServie service(){

}

}
```

##  18.SpringBoot Web 怎么打包？  war    jar ?

通常，我们将 web 应用程序打包成 WAR 文件，然后将它部署到另外的服务器上。这样做使得我们能够在相同的服务器上处理多个项目。当 CPU 和内存有限的情况下，这是一种最好的方法来节省资源。

然而，事情发生了转变。现在的计算机硬件相比起来已经很便宜了，并且现在的注意力大多转移到服务器配置上。部署中对服务器配置的一个细小的失误都会导致无可预料的灾难发生。

Spring 通过提供插件来解决这个问题，也就是 ***spring-boot-maven-plugin*** 来打包 web 应用程序到一个额外的 JAR 文件当中。为了引入这个插件，只需要在 *pom.xml* 中添加一个 *plugin* 属性：

```
1 <plugin>
2     <groupId>org.springframework.boot</groupId>
3     <artifactId>spring-boot-maven-plugin</artifactId>
4 </plugin>
```

有了这个插件，**在执行package 步骤后得到一个jar包，**这个jar包包含了所需的所有依赖以及一个嵌入的服务器。因此，我们不需要担心去配置一个额外的服务器了。**运行一个普通的jar包启动应用程序**。

打成jar包 ，pom.xml:

<packaging>jar</packaging>

打成war 包： pom.xml :

<packaging>war</packaging>

并且将容器依赖从打包文件中移除

```
<dependency>
2     <groupId>org.springframework.boot</groupId>
3     <artifactId>spring-boot-starter-tomcat</artifactId>
4     <scope>provided</scope>
5 </dependency>
```

执行 Maven 的 *package* 步骤之后，我们得到一个可部署的 WAR 文件。



### 19.怎么使用 SpringBoot 去执行命令行程序？

其他 Java 程序一样，一个 SpringBoot 命令行程序必须要有一个 *main* 方法。这个方法作为一个入口点，通过调用 *SpringApplication#run* 方法来驱动程序执行“

```
@SpringBootApplicationpublic
class MyApplication {    
public static void main(String[] args) {

SpringApplication.run(MyApplication.class); 
// other statements    }}
```

*SpringApplication* 类会启动一个 Spirng 容器以及自动化配置 beans。
要注意的是我们必须把一个配置类传递到 *run* 方法中作为首要配置资源。按照惯例，这个参数一般是入口类本身。
在调用 *run* 方法之后，我们可以像平常的程序一样执行其他语句。

## 20.有什么外部配置的可能来源？

```
SpringBoot 对外部配置提供了支持，允许我们在不同环境中运行相同的应用。我们可以使用 properties 文件、YAML 文件、环境变量、系统参数和命令行选项参数来声明配置属性。
然后我们可以通过 @Value 这个通过 @ConfigurationProperties 绑定的对象的注解或者实现Enviroment 来访问这些属性。
以下是最常用的外部配置来源：

命令行属性：命令行选项参数是以双连字符（例如，=）开头的程序参数，例如 –server.port=8080。SpringBoot将所有参数转换为属性并且添加到环境属性当中。
应用属性：应用属性是指那些从 application.properties 文件或者其 YAML 副本中获得的属性。默认情况下，SpringBoot会从当前目录、classpath 根目录或者它们自身的 config 子目录下搜索该文件。
特定 profile 配置：特殊概要配置是从 application-{profile}.properties 文件或者自身的 YAML 副本。{profile} 占位符引用一个在用的 profile。这些文件与非特定配置文件位于相同的位置，并且优先于它们。
```

## 21.SpringBoot 支持送绑定代表什么？



### 22.您使用了哪些 starter maven 依赖项？

使用了下面的一些依赖项

spring-boot-starter-activemq

spring-boot-starter-security

这有助于增加更少的依赖关系，并减少版本的冲突。

### 23.SpringBoot 的启动流程

https://blog.csdn.net/wanderlustlee/article/details/80350736

```
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
```

##### @SpringBootApplication注解

```
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
		@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
public @interface SpringBootApplication {
```

**@EnableAutoConfiguration**

```
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AutoConfigurationPackage
@Import(AutoConfigurationImportSelector.class)
public @interface EnableAutoConfiguration
```

 其中：

```
@Import(AutoConfigurationImportSelector.class)
```



```
AutoConfigurationImportSelector类
```

```
AutoConfigurationMetadata autoConfigurationMetadata = AutoConfigurationMetadataLoader
      .loadMetadata(this.beanClassLoader);
```



```
static AutoConfigurationMetadata loadMetadata(ClassLoader classLoader, String path) {
		try {
			Enumeration<URL> urls = (classLoader != null) ? classLoader.getResources(path)
					: ClassLoader.getSystemResources(path);
			Properties properties = new Properties();
```

### @ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),

组件扫描，可自动发现和装配Bean，功能其实就是自动扫描并加载符合条件的组件或者bean定义，最终将这些bean定义加载到IoC容器中。可以通过basePackages等属性来细粒度的定制@ComponentScan自动扫描的范围，如果不指定，则默认Spring框架实现会从声明**@ComponentScan**所在类的package进行扫描。默认扫描SpringApplication的run方法里的Booter.class所在的包路径下文件，所以最好将该启动类放到根包路径下。



### SpringApplication.run 方法

```
	public static ConfigurableApplicationContext run(Class<?>[] primarySources, String[] args) {
		return new SpringApplication(primarySources).run(args);
	}
```



### 24.SpringBoot 怎么解决跨域问题

跨域问题可以前端通过JSONP 来解决，但是JSONP 只可以发送GET请求。无法发送其他类型的请求。在restful 风格的应用，显得非常鸡肋。因此我们推荐通过后端CORS来解决跨域问题。这种解决方案并非SpringBoot特有的额在传统的SSM框架中，就可以通过CORS 来解决。以前是SSM上是通过XML 配置，现在是通过@CrossOrgin注解来解决跨域问题.

#### 25.Spring  Security 和Shiro 各自特点。

Spring Security 是重量级安全框架。Shiro 和Spring Security 则是一个轻量级的框架。

Spring Security 概念复杂 配置繁琐，Shiro 配置简单，概念简单。

Spring Security 功能强大。 Shiro 功能简单。

26. 分布式session 

    多个微服务各自的session 被物理空间隔开通过Spring Session +Redis 来实现session 

    参看SpringBoot 整合Session 共享。

27. SpringBoot  定时任务怎么实现的？

     方法1：

    @Scheduled 注解

    方法2：

    第三方框架Quartz

    