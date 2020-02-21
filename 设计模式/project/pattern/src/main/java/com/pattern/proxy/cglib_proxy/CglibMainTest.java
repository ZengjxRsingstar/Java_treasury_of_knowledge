package com.pattern.proxy.cglib_proxy;

/**
 * @ClassName CglibMainTest
 * @Description TODO
 * @Author zengjx
 * @Company zengjx
 * @Date 2020/2/18  16:19
 * @Version V1.0
 */
public class CglibMainTest {
    public static void main(String[] args) {

        //生成Cglib代理类
        Engineer  engineerProxy =(Engineer)CglibProxy.getProxy(new Engineer());

        // 调用相关方法----public 会覆盖
        engineerProxy.eat();
        //final方法不会被覆盖
        engineerProxy.work();
        //private 方法不会被覆盖

    }
}
