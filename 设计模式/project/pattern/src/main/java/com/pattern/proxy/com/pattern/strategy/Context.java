package com.pattern.proxy.com.pattern.strategy;

/**
 * @ClassName HelloController
 * @Description TODO
 * @Author zengjx
 * @Company zengjx
 * @Date 2020/2/20  22:24
 * @Version V1.0
 */
public class Context {
    private   Strategy  strategy;

    public  Context (Strategy  strategy){
        this.strategy=strategy;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
    public  int executeStrategy(int num1,int num2){
        return  strategy.doOperation(num1, num2);

    }
}
