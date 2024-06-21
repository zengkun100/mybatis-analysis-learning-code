package com.linkedbear.mybatis.app;

import com.linkedbear.mybatis.entity.Department;
import com.linkedbear.mybatis.mapper.DepartmentMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.Properties;

public class LoadPropertiesApplication {
    
    public static void main(String[] args) throws Exception {
        InputStream xml = Resources.getResourceAsStream("mybatis-config.xml");
        Properties properties = new Properties();
        // 手动加载多个配置文件，合并成一个
        properties.load(Resources.getResourceAsStream("jdbc1.properties"));
        properties.load(Resources.getResourceAsStream("jdbc2.properties"));
        
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(xml, properties);
        try( SqlSession sqlSession = sqlSessionFactory.openSession()) {
            DepartmentMapper departmentMapper = sqlSession.getMapper(DepartmentMapper.class);
            Department department = departmentMapper.findById("18ec781fbefd727923b0d35740b177ab");
            System.out.println(department);
            System.out.println(department.getUsers());
        }


    }
}
