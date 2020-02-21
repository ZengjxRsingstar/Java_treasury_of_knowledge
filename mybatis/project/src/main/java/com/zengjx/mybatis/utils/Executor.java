package com.zengjx.mybatis.utils;



import com.zengjx.mybatis.cfg.Mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
public class Executor {

    public static <E> List<E> selectList(Connection connection, Mapper mapper) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 获取sql语句
            String queryString = mapper.getSql();
            // 封装结果类型
            String resultType = mapper.getResultType();
            ps = connection.prepareStatement(queryString);
            rs = ps.executeQuery();
            // 封装（反射）
            return Converter.list(rs,Class.forName(resultType));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(ps!=null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
