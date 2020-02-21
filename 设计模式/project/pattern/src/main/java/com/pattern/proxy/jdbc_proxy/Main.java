package com.pattern.proxy.jdbc_proxy;

import java.lang.reflect.Proxy;

/**
 * @ClassName HelloController
 * @Description TODO
 * @Author zengjx
 * @Company zengjx
 * @Date 2020/2/18  14:56
 * @Version V1.0
 */
public class Main {

    public static void main(String[] args) {
      //真实对象
        Subject realSubject =  new RealSubject();

        MyInvovationHandler myInvocationHandler = new MyInvovationHandler(realSubject);
       Subject  subject= (Subject)Proxy.newProxyInstance(RealSubject.class.getClassLoader(),
                RealSubject.class.getInterfaces(),myInvocationHandler
                );
        subject.sellBooks();
        subject.speak();
    }
}
