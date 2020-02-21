package com.pattern.proxy.observer_proxy;

/**
 * @ClassName HelloController
 * @Description TODO
 * @Author zengjx
 * @Company zengjx
 * @Date 2020/2/21  15:38
 * @Version V1.0
 */
public abstract class Observer {
   protected     Subject subject;
    public   abstract   void   update();
}
