package com.pattern.proxy.com.pattern.strategy;

import org.junit.Test;

/**
 * @ClassName HelloController
 * @Description TODO
 * @Author zengjx
 * @Company zengjx
 * @Date 2020/2/20  22:26
 * @Version V1.0
 */
public class StrategyPatternDemo {


  @Test
    public  void test () {

       Context context =new Context(new OperationAdd() );
        int strategy = context.executeStrategy(10, 20);
        System.out.println(strategy);
       context.setStrategy(new OperationMultipy());
        int strategy2 = context.executeStrategy(10, 20);
        System.out.println(strategy2);
      context.setStrategy(new OperationSubstrct());
       int strategy3 = context.executeStrategy(10, 20);
      System.out.println(strategy3);
    }
}
