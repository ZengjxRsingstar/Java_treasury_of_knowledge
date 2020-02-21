package com.zengjx.mybatis.utils;

import java.io.InputStream;

public class Resources {
    public static InputStream getResourceAsStream(String path) {
        return Resources.class.getClassLoader().getResourceAsStream(path);
    }
}
