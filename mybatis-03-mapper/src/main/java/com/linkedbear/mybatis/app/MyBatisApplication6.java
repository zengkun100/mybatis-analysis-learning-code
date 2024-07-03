package com.linkedbear.mybatis.app;

import com.linkedbear.mybatis.entity.Department;
import com.linkedbear.mybatis.mapper.DepartmentMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

public class MyBatisApplication6 {
    
    public static void main(String[] args) throws Exception {
        InputStream xml = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(xml);
        DepartmentMapper departmentMapper;
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            departmentMapper = sqlSession.getMapper(DepartmentMapper.class);
            Department department = departmentMapper.findById("18ec781fbefd727923b0d35740b177ab");
            System.out.println(department);
            System.out.println(department.getUsers());
        }
    }
}
