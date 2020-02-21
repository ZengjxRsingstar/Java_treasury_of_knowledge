package com.zengjx.mybatis.cfg;

import java.util.HashMap;
import java.util.Map;

public class Configuration {
    private String driver;//数据库驱动
    private String url;//jdbcurl
    private String username;
    private String password;

    /**
     * key:  映射器全限定类名 +  方法名
     * value： Mapper对象，里边有：sql语句和resultType
     */
    private Map<String, Mapper> mappers = new HashMap<>();

    public Map<String, Mapper> getMappers() {
        return mappers;
    }

    public void setMappers(Map<String, Mapper> mappers) {
        this.mappers.putAll(mappers);
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
