package com.pattern.proxy.observer_proxy;

/**
 * @ClassName HelloController
 * @Description TODO
 * @Author zengjx
 * @Company zengjx
 * @Date 2020/2/21  15:40
 * @Version V1.0
 */
public class OctalObserver extends   Observer {

    OctalObserver(Subject  subject){
        this.subject=subject;
        this.subject.attach(this);
    }
    @Override
    public void update() {
        System.out.println("Octal string   "+Integer.toOctalString(subject.getState()));
    }
}
