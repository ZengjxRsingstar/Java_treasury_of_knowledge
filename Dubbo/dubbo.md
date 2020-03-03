### 1.什么是RPC?

remote procedure call  远程过程调用。通过网络

### 2.RPC框架有哪一些？

 RMI  Hessian   Dubbo

### 3.Dubbo的三大核心： 面向接口的远程方法调用，智能容错和负载均衡，服务自动注册和发现。

4.画一下 Dubbo的架构图

![](.\img\Dubbo架构图.png)

节点角色说明：

| **节点****** | **角色名称******                       |
| ------------ | -------------------------------------- |
| Provider     | 暴露服务的服务提供方                   |
| Consumer     | 调用远程服务的服务消费方               |
| Registry     | 服务注册与发现的注册中心               |
| Monitor      | 统计服务的调用次数和调用时间的监控中心 |
| Container    | 服务运行容器                           |

**虚线都是异步访问，实线都是同步访问**

蓝色虚线:**在启动时完成的功能红色虚线(实线)都是程序运行过程中执行的功能**

调用关系说明:

0：服务容器负责启动，加载，运行服务提供者。

1：服务提供者在启动时，向注册中心注册自己提供的服务。

2：服务消费者在启动时，向注册中心订阅自己所需的服务。

3：注册中心返回服务提供者地址列表给消费者，如果有变更，注册中心将基于==长连接==通知推送变更数据给消费者。

4：服务消费者，从提供者地址列表中，基于软负载均衡算法，选一台提供者进行调用，如果调用失败，再选另一台调用。

5：服务消费者和提供者，在内存中累计调用次数和调用时间，定时每分钟发送一次统计数据到监控中心。

### 3.《dubbo与dubbox与springcloud三者比较》

<https://blog.csdn.net/qq_30764991/article/details/81221892

### 4.Zookeeper 是什么？

Dubbo官方推荐使用Zookeeper作为服务注册中心。Zookeeper 是 Apache Hadoop 的子项目，是一个树型的目录服务，支持变更推送，适合作为 Dubbo 服务的注册中心

## 5.Zookeeper 树形目录

![](.\img\Zookeeper树形目录.png)

流程说明：

· 

1：**服务提供者(Provider)启动时**: 向 /dubbo/com.foo.BarService/providers 目录下写入自己的 URL 地址

2：**服务消费者(Consumer)启动时**: 订阅 /dubbo/com.foo.BarService/providers 目录下的提供者 URL 地址。并向 /dubbo/com.foo.BarService/consumers 目录下写入自己的 URL 地址

3：**监控中心(Monitor)启动时:** 订阅 /dubbo/com.foo.BarService 目录下的所有提供者和消费者 URL 地址



### 在服务消费者工程(dubbo_day01_consumer)中只是引用了HelloService接口，并没有提供实现类，Dubbo是如何做到远程调用的？

**答：**Dubbo底层是基于**代理技术为HelloService接口创建代理对象**，远程调用是通过此代理对象完成的。可以通过开发工具的debug功能查看此代理对象的内部结构。另外，==Dubbo实现网络传输底层是基于Netty框架（封装了原始的网络编程接口Socket）完成的==。

### Zookeeper服务就变得非常重要了，那如何防止Zookeeper单点故障呢？

**答：**Zookeeper其实是支持集群模式的，可以配置Zookeeper集群来达到Zookeeper服务的高可用，防止出现单点故障。（可参考：《Zookeeper集群搭建.docx》）

###  ==dubbo协议：==

- 连接个数：单连接
- 连接方式：长连接
- 传输协议：TCP
- 传输方式：NIO异步传输
- 序列化：Hessian二进制序列化
- 适用范围：传入传出参数数据包较小（建议小于100K），消费者比提供者个数多，单一消费者无法压满提供者，尽量不要用dubbo协议传输大文件或超大字符串。
- 适用场景：常规远程服务方法调用

==rmi协议：==

- 连接个数：多连接
- 连接方式：短连接
- 传输协议：TCP
- 传输方式：同步传输
- 序列化：Java标准二进制序列化
- 适用范围：传入传出参数数据包大小混合，消费者与提供者个数差不多，可传文件。
- 适用场景：常规远程服务方法调用，与原生RMI服务互操作

详情使用可通过：<https://www.cnblogs.com/duanxz/p/3555876.html>了解

负载均衡（Load Balance）：其实就是将请求分摊到多个操作单元上进行执行，从而共同完成工作任务。

在集群负载均衡时，Dubbo 提供了多种均衡策略（包括==随机==、轮询、最少活跃调用数、一致性Hash），缺省为random随机调用。

配置负载均衡策略，既可以在服务提供者一方配置，也可以在服务消费者一方配置，如下：

第一步：@Reference(check = false)添加：loadbalance = "random"