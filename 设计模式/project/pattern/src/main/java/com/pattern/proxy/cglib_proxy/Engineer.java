package com.pattern.proxy.cglib_proxy;

/**
 * @ClassName Engineer
 * @Description TODO
 * @Author zengjx
 * @Company zengjx
 * @Date 2020/2/18  16:08
 * @Version V1.0
 */
public class Engineer {
    // 可以被代理
    public void eat() {
        System.out.println("工程师正在吃饭----public 会被覆盖");
    }

    // final 方法不会被生成的字类覆盖
    public final void work() {
        System.out.println("工程师正在工作---final型");
        play();
    }

    // private 方法不会被生成的字类覆盖
    private void play() {
        System.out.println("this engineer is playing game 该方法不会被代理覆盖");
    }
}
