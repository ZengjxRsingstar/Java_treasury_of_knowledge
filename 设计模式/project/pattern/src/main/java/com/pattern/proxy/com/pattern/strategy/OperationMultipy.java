package com.pattern.proxy.com.pattern.strategy;

/**
 * @ClassName HelloController
 * @Description TODO
 * @Author zengjx
 * @Company zengjx
 * @Date 2020/2/20  22:18
 * @Version V1.0
 */
public class OperationMultipy  implements  Strategy {
    @Override
    public int doOperation(int num1, int numb2) {
        return num1*numb2;
    }
}
