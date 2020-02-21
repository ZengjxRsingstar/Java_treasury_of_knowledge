package com.pattern.proxy.cglib_proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @ClassName CglibProxy
 * @Description TODO
 * @Author zengjx
 * @Company zengjx
 * @Date 2020/2/18  16:09
 * @Version V1.0
 */
public class CglibProxy implements MethodInterceptor {
    private Object target;

    public CglibProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        System.out.println("###   before invocation");
        Object result = method.invoke(target, objects);
        System.out.println("###   end invocation");

        return result;
    }

    public static Object getProxy(Object target){
        Enhancer enhancer =new Enhancer();
        //设置需要的代理对象
        enhancer.setSuperclass(target.getClass());
        //设置代理人
        enhancer.setCallback(new CglibProxy(target));
        return  enhancer.create();


    }
}
