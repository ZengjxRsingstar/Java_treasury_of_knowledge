### 消息幂等性问题，消息重复消费怎么解决？

微人事项目：rabbitmq 重复消费问题

https://mp.weixin.qq.com/s/SMPyyZlRvvKM-kSMbOOQAw

在rabbitmq 的@RabbitListener 修饰的消息处理方法中， 消息唯一id 保存在redis   hash中。如果redis 已经有这个key 那怎那么会，说明该消息已经消费。

### **如何防止消息丢失**？

1.2  幸运的是RabbitMQ提供了一个改进方案，即发送方确认机制（publisher confirm）
首先生产者通过调用channel.confirmSelect方法将信道设置为confirm模式，一旦信道进入confirm模式，所有在该信道上面发布的消息都会被指派一个唯一的ID（从1开始），一旦消息被投递到所有匹配的队列之后，RabbitMQ就会发送一个确认（Basic.Ack）给生产者（包含消息的唯一deliveryTag和multiple参数），这就使得生产者知晓消息已经正确到达了目的地了。
其实Confirm模式有三种方式实现：

串行confirm模式：producer每发送一条消息后，调用waitForConfirms()方法，等待broker端confirm，如果服务器端返回false或者在超时时间内未返回，客户端进行消息重传。
批量confirm模式：producer每发送一批消息后，调用waitForConfirms()方法，等待broker端confirm。
异步confirm模式：提供一个回调方法，broker confirm了一条或者多条消息后producer端会回调这个方法。
我们分别来看看这三种confirm模式
————————————————
版权声明：本文为CSDN博主「fly-rock」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/weixin_38087443/article/details/100167717

### 如何保证消息的可靠性？高可用



**解决RabbitMQ消息丢失问题和保证消息可靠性（一）**

1. RabbitMQ 消息丢失场景有哪些？
2. 如何避免消息丢失？
3. 如何设计部署消息中间件保证消息可靠性？

****RabbitMQ Server中存储的消息如何保证消息可靠性和高可用****