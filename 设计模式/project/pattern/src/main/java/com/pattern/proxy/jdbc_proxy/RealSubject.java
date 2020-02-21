package com.pattern.proxy.jdbc_proxy;

/**
 * @ClassName RealSubject
 * @Description TODO
 * @Author zengjx
 * @Company zengjx
 * @Date 2020/2/18  14:47
 * @Version V1.0
 */
public class RealSubject implements  Subject {


    @Override
    public int sellBooks() {

        System.out.println("卖书");
        return 0;
    }

    @Override
    public String speak() {
        System.out.println("说话");
        return null;
    }
}
