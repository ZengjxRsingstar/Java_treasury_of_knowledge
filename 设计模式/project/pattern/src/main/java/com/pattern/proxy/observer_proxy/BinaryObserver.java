package com.pattern.proxy.observer_proxy;

/**
 * @ClassName HelloController
 * @Description TODO
 * @Author zengjx
 * @Company zengjx
 * @Date 2020/2/21  15:39
 * @Version V1.0
 */
public class BinaryObserver  extends   Observer {


    BinaryObserver(Subject  subject){

         this.subject=subject;
         this.subject.attach(this);
    }
    @Override
    public void update() {
        System.out.println("BinaryObserver string   "+Integer.toBinaryString(subject.getState()));
    }
}
