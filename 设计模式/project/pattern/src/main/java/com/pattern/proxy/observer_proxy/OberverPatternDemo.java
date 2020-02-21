package com.pattern.proxy.observer_proxy;

/**
 * @ClassName HelloController
 * @Description TODO
 * @Author zengjx
 * @Company zengjx
 * @Date 2020/2/21  16:03
 * @Version V1.0
 */
public class OberverPatternDemo {
    public static void main(String[] args) {

        Subject subject = new Subject();
        new HexaObserver(subject);
        new OctalObserver(subject);
        new BinaryObserver(subject);
        System.out.println("First number ");
        subject.setState(12);
        subject.notifyAllObserver();

        System.out.println("Tow  number");
        subject.setState(30);
        subject.notifyAllObserver();



    }
}
