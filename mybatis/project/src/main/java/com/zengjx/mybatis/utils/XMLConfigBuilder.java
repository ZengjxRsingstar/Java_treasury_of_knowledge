package com.zengjx.mybatis.utils;

import com.zengjx.mybatis.cfg.Configuration;
import com.zengjx.mybatis.cfg.Mapper;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class XMLConfigBuilder {
    /**
     * 解析xml，把所有配置信息封装到Configuration对象
     * @param inputStream xml文件的流
     * @return Configuration
     */
    public Configuration parse(InputStream inputStream) {
        //读取核心配置文件里的数据库连接信息，封装到Configuration对象
        Configuration configuration = new Configuration();
        try {
            //1. 创建一个解析器对象
            SAXReader reader = new SAXReader();
            //2. 读取xml，得到Document
            Document document = reader.read(inputStream);
            //3. 获取数据库连接信息
            Element element = (Element) document.selectSingleNode("//property[@name='driver']");
            String value = element.attributeValue("value");
            configuration.setDriver(value);

            element = (Element) document.selectSingleNode("//property[@name='url']");
            value = element.attributeValue("value");
            configuration.setUrl(value);

            element = (Element) document.selectSingleNode("//property[@name='username']");
            value = element.attributeValue("value");
            configuration.setUsername(value);

            element = (Element) document.selectSingleNode("//property[@name='password']");
            value = element.attributeValue("value");
            configuration.setPassword(value);

            //封装每个映射配置文件里的每个statement的信息：SQL语句，结果集封装类型
            List<Node> nodes = document.selectNodes("//mappers/mapper");
            for (Node node : nodes) {
                element = (Element) node;
                //获取每个映射配置文件的路径
                String resource = element.attributeValue("resource");
                //调用方法，把每个映射配置文件的信息，封装到Configuration里
                getMappers(resource, configuration);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return configuration;
    }

    /**
     * 封装每个映射配置文件
     * @param resource  xml配置文件的路径
     * @param configuration  配置信息对象
     */
    private void getMappers(String resource, Configuration configuration) {
        SAXReader reader = new SAXReader();
        InputStream inputStream = Resources.getResourceAsStream(resource);
        try {
            Document document = reader.read(inputStream);

            Element rootElement = document.getRootElement();

            //获取映射器的全限定类名
            String mapperInterfaceClassName = rootElement.attributeValue("namespace");

            //获取这个映射器里所有的statement
            List<Element> elements = rootElement.elements();
            for (Element element : elements) {
                //获取方法名
                String methodName = element.attributeValue("id");
                //获取SQL语句
                String sql = element.getText();
                //获取结果集封装类型
                String resultType = element.attributeValue("resultType");

                //把sql和结果集类型封装成Mapper对象
                Mapper mapper = new Mapper();
                mapper.setSql(sql);
                mapper.setResultType(resultType);

                //把Mapper对象放到Configuration里
                String key = mapperInterfaceClassName + "." +methodName;
                Map<String, Mapper> mappers = configuration.getMappers();
                mappers.put(key, mapper);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
