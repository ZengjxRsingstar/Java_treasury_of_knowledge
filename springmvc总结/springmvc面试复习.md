SpringMVC

## 1.什么是SpringMVC

SpringMVC是一种基于Java的实现MVC设计模型的请求驱动类型的轻量级Web框架，通过一套注解，让一个简单的Java类成为处理请求的控制器，而无需实现任何接口。同时它还支持RESTful风格编程风格的请求。

## 2.SpringMVC的控制器

**前端控制器（DispatcherServlet）**

用户请求到达前端控制器，它就相当于 mvc 模式中的 c， dispatcherServlet 是整个流程控制的中心，由
它调用其它组件处理用户的请求， dispatcherServlet 的存在降低了组件之间的耦合性  

**请求到处理器映射（HandlerMapping）**

HandlerMapping 负责根据用户请求找到 Handler 即处理器， SpringMVC 提供了不同的映射器实现不同的
映射方式，例如：配置文件方式，实现接口方式，注解方式等。  

**处理器适配器（HandlerAdapter）**

它就是我们开发中要编写的具体业务控制器。由 DispatcherServlet 把用户请求转发到 Handler。由
Handler 对具体的用户请求进行处理。  

View Resolver 负责将处理结果生成 View 视图， View Resolver 首先根据逻辑视图名解析成物理视图名
即具体的页面地址，再生成 View 视图对象，最后对 View 进行渲染将处理结果通过页面展示给用户。  

**处理器或页面控制器（Controller）**

**view Resolver 视图解析器**

View Resolver 负责将处理结果生成 View 视图， View Resolver 首先根据逻辑视图名解析成物理视图名
即具体的页面地址，再生成 View 视图对象，最后对 View 进行渲染将处理结果通过页面展示给用户。  

**View** 

SpringMVC 框架提供了很多的 View 视图类型的支持，包括： jstlView、 freemarkerView、 pdfView
等。我们最常用的视图就是 jsp。
一般情况下需要通过页面标签或页面模版技术将模型数据通过页面展示给用户，需要由程序员根据业务需求开
发具体的页面。  

**验证器（ Validator）**

**命令对象（Command 请求参数绑定到的对象就叫命令对象）**
表单对象（Form Object 提供给表单展示和提交到的对象就叫表单对象）。  

## 3.SpringMVC的请求处理流程图

![](.\img\执行流程原理.jpg)

## 4.SpringMVC 和 Struts2 的优略分析  

共同点：

它们都是表现层框架，都是基于 MVC 模型编写的。
它们的底层都离不开原始 ServletAPI。
它们处理请求的机制都是一个核心控制器  

区别：

Spring MVC 的入口是 Servlet, 而 Struts2 是 Filter
Spring MVC 是基于方法设计的，而 Struts2 是基于类， Struts2 每次执行都会创建一个动作类。所
以 Spring MVC 会稍微比 Struts2 快些。
Spring MVC 使用更加简洁,同时还支持 JSR303, 处理 ajax 的请求更方便  

(**JSR303 是一套 JavaBean 参数校验的标准，它定义了很多常用的校验注解，我们可以直接将这些注**
**解加在我们 JavaBean 的属性上面，就可以在需要校验的时候进行校验了。** )  

Struts2 的 OGNL 表达式使页面的开发效率相比 Spring MVC 更高些，但执行效率并没有比 JSTL 提
升，尤其是 struts2 的表单标签，远没有 html 执行效率高。  

## 5.案例执行流程

![](.\img\案例执行流程.png)

1.服务器启动，应用被加载。读到web.xml中的配置创建 spring 容器并且初始化容器中的对象。  

2.浏览器发送请求，被DispatherServlet捕获，但是该Servlet 不处理请求，而是把请求转发出去。转发的路径是根基请求URL,匹配@RequestMaping中的内容。

3.匹配到了后执行对应的方法，该方法有一个返回值。

4.根据方法的返回值,借助InternalResourceViewResolver找到对应的结果视图。

5.渲染结果视图，响应浏览器。

## 6.常用注解

### @RequestMaping 

出现在方法和类上，用于建立URL和处理请求方法之上。

属性值：value 是请求URL地址

method 用于指定请求的方法：post  ,get.

params用于指定请求参数，要求请求参数必须和配置一样

params = {"accountName"}，表示请求参数必须有 accountName  

### **@RequestParam**

**把请求中指定名称的参数给控制器中的形参赋值**  

属性：
value： 请求参数中的名称。
required：请求参数中是否必须提供此参数。 默认值： true。表示必须提供，如果不提供将报错。  

```
<a href="springmvc/useRequestParam?name=test">requestParam 注解</a>
```

```
@RequestMapping("/useRequestParam")
public String useRequestParam(@RequestParam("name")String username,
@RequestParam(value="age",required=false)Integer age){
```

### @RequestBody 

 作用：
**用于获取请求体内容。** 直接使用得到是 key=value&key=value...结构的数据。
get 请求方式不适用。
属性：
required：是否必须有请求体。默认值是:true。当取值为 true 时,get 请求方式会报错。如果取值
为 false， get 请求得到是 null。  

```
post 请求 jsp 代码：
<!-- request body 注解 -->
<form action="springmvc/useRequestBody" method="post">
用户名称： <input type="text" name="username" ><br/>
用户密码： <input type="password" name="password" ><br/>
用户年龄： <input type="text" name="age" ><br/>
<input type="submit" value="保存">
</form>
get 请求 jsp 代码：
<a href="springmvc/useRequestBody?body=test">requestBody 注解 get 请求</a>
控制器代码：
@RequestMapping("/useRequestBody")
public String useRequestBody(@RequestBody(required=false) String body){
System.out.println(body);
return "success";
}
```



### @PathVaribale

  作用：
用于绑定 url 中的占位符。 例如：请求 url 中 /delete/{id}， 这个{id}就是 url 占位符。
url 支持占位符是 spring3.0 之后加入的。是 springmvc 支持 rest 风格 URL 的一个重要标志。
属性：
value： 用于指定 url 中占位符名称。
required：是否必须提供占位符  

前端

```
<a href="springmvc/usePathVariable/100">pathVariable 注解</a> 

```

```
@RequestMapping("/usePathVariable/{id}")
public String usePathVariable(@PathVariable("id") Integer id){
System.out.println(id);
return "success";
}

```



## 7.SpringMVC 请求参数

- 基本类型参数：
  包括基本类型和 String 类型

- 要求我们的参数名称必须和控制器中方法的形参名称保持一致。 **(严格区分大小写)**  POJO 类型参数：
  包括实体类，以及关联的实体类

  ```
  @RequestMapping("/saveAccount")
  public String saveAccount(Account account)
  ```

  pojo 包含集合：

  ```
  public class User implements Serializable {
  private String username;
  private String password;
  private Integer age;
  private List<Account> accounts;
  private Map<String,Account> accountMap;
  
  ```

  ```
  @RequestMapping("/updateAccount")
  public String updateAccount(User user)
  ```

  

要求表单中参数名称和 POJO 类的属性名称保持一致。并且控制器方法的参数类型是 POJO 类型。

-   数组和集合类型参数：
  包括 List 结构和 Map 结构的集合（包括数组）
  SpringMVC 绑定请求参数是自动实现的，但是要想使用，必须遵循使用要求。

- 如果是集合类型

  第一种：
  要求集合类型的请求参数必须在 POJO 中。在表单中请求参数名称要和 POJO 中集合属性名称相同。
  给 List 集合中的元素赋值， 使用下标。
  给 Map 集合中的元素赋值， 使用键值对。
  第二种：
  接收的请求参数是 json 格式数据。需要借助一个注解实现。    

## 8.请求参数乱码问题

```
post 请求方式：
在 web.xml 中配置一个过滤器
<!-- 配置 springMVC 编码过滤器 -->
<filter>
<filter-name>CharacterEncodingFilter</filter-name>
设置过滤器的属性
启动过滤器
设置哪些需要过滤
```

```
get 请求方式：
tomacat 对 GET 和 POST 请求处理方式是不同的， GET 请求的编码问题， 要改 tomcat 的 server.xml
配置文件，如下：
<Connector connectionTimeout="20000" port="8080"
protocol="HTTP/1.1" redirectPort="8443"/>
改为：
<Connector connectionTimeout="20000" port="8080"
protocol="HTTP/1.1" redirectPort="8443"
useBodyEncodingForURI="true"/>
如果遇到 ajax 请求仍然乱码，请把：
useBodyEncodingForURI="true"改为 URIEncoding="UTF-8"
即可。
```

## 9.自定义类型转换器

前端请求是：<a href="account/deleteAccount?date=2018-01-01">根据日期删除账户</a

controller:@RequestMapping("/deleteAccount")
public String deleteAccount(Date date)   

此时会报400

解决方法：

1.定义一个类实现Converter<S,T>接口   S 表示接受类型，T 目标类型。convert 方法里面对String 使用Simple DateFormat（“yyyy-MM-dd”）转为Date.

2,在spring 配置文件中通过配置类型转换工程ConversionServiceFactoryBean注入一个新的StringToDateConverter  类型转换配置转换器，将自定义的转换器注册到类型转换服务。

3.在 annotation-driven 标签中引用配置的类型转换服务  

## 10.ServerletAPI作为请求参数

SpringMVC 还支持使用原始 ServletAPI 对象作为控制器方法的参数。支持原始 ServletAPI 对象有：
HttpServletRequest
HttpServletResponse
HttpSession
java.security.Principal
Locale
InputStream
OutputStream
Reader
Writer  

## 11.restful 风格

它本身并没有什么实用性，其核心价值在于如何设计出符合 REST 风格的网络接口。
restful 的优点
它结构清晰、符合标准、易于理解、 扩展方便，所以正得到越来越多网站的采用。
restful 的特性：
资源（Resources） ： 网络上的一个实体，或者说是网络上的一个具体信息。
它可以是一段文本、一张图片、一首歌曲、一种服务，总之就是一个具体的存在。可以用一个 URI（统一
资源定位符）指向它，每种资源对应一个特定的 URI 。要
获取这个资源，访问它的 URI 就可以，因此 URI 即为每一个资源的独一无二的识别符。
表现层（Representation） ： 把资源具体呈现出来的形式，叫做它的表现层 （Representation）。
比如，文本可以用 txt 格式表现，也可以用 HTML 格式、 XML 格式、 JSON 格式表现，甚至可以采用二
进制格式。
状态转化（State Transfer） ： 每 发出一个请求，就代表了客户端和服务器的一次交互过程。
HTTP 协议，是一个无状态协议，即所有的状态都保存在服务器端。因此，如果客户端想要操作服务器，
必须通过某种手段， 让服务器端发生“状态转化” （State Transfer）。而这种转化是建立在表现层之上的，所以
就是 “表现层状态转化” 。具体说，就是 HTTP 协议里面，四个表示操作方式的动词： GET 、 POST 、 PUT、
DELETE。它们分别对应四种基本操作： GET 用来获取资源， POST 用来新建资源， PUT 用来更新资源， DELETE 用来
删除资源。
restful 的示例：
/account/1 HTTP GET ： 得到 id = 1 的 account
/account/1 HTTP DELETE： 删除 id = 1 的 account
/account/1 HTTP PUT： 更新 id = 1 的 account  