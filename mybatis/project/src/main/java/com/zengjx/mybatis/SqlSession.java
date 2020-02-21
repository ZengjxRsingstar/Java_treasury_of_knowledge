package com.zengjx.mybatis;

public interface SqlSession {
    <T> T getMapper(Class<T> mapperInterfaceClass);

    void close();

}
