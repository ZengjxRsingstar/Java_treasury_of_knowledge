package com.zengjx.mybatis.cfg;

public class Mapper {
    private String sql;
    private String resultType;

    public String getSql() {
        return sql;
    }//获取Sql语句

    public void setSql(String sql) {
        this.sql = sql;
    }//设置sql 语句

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }
}
