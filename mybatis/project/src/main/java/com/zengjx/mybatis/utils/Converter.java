package com.zengjx.mybatis.utils;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Converter {
    /***
     * 将ResultSet转换成List集合
     * @param set
     * @param clazz
     * @param <E>
     * @return
     */
    public static <E> List<E> list(ResultSet set, Class clazz){
        try {
            //定义一个List集合对象
            List<E> list = new ArrayList<E>();

            //获取所有属性名
            Field[] fields = clazz.getDeclaredFields();

            //循环封装
            while (set.next()){
                //获取当前Class的实例
                Object instance = clazz.newInstance();

                //如果属性名和列名一样时，直接可以从ResultSet中根据列名取数据
                for (Field field : fields) {
                    //把属性名当数据库表列名去取数据
                    Object result = set.getObject(field.getName());
                    //给对应的属性赋值
                    if(result!=null){
                        //获取访问属性的权限
                        field.setAccessible(true);
                        //给该属性赋值
                        field.set(instance,result);
                    }
                }
                //将实例加入到集合中
                list.add((E) instance);
            }

            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
}
