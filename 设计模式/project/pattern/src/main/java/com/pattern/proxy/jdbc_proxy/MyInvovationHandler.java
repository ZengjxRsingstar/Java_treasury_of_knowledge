package com.pattern.proxy.jdbc_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @ClassName MyInvovationHandler
 * @Description TODO
 * @Author zengjx
 * @Company zengjx
 * @Date 2020/2/18  14:49
 * @Version V1.0
 */
public class MyInvovationHandler  implements InvocationHandler {

    private  Subject  subject;

    //1.注入真实对象
    MyInvovationHandler(Subject  subject){
        this.subject=subject;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("调用代理类");
        if(method.getName().equals("sellBooks")){
            Object invoke = method.invoke(subject, args);
            System.out.println("代理类日志-调用的是卖书的方法");
            return  invoke;
        }
        if(method.getName().equals("speak")){
            String string = (String) method.invoke(subject,args) ;
            System.out.println("代理类日志--调用的是说话的方法");
            return  string ;
        }

        return null;
    }
}
