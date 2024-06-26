package com.linkedbear.mybatis.app;

import com.linkedbear.mybatis.entity.Department;
import com.linkedbear.mybatis.mapper.DepartmentMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

public class MyBatisApplication6 {
    
    public static void main(String[] args) throws Exception {
        InputStream xml = Resources.getResourceAsStream("mybatis-config-4.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(xml);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            DepartmentMapper departmentMapper = sqlSession.getMapper(DepartmentMapper.class);
/*
            Department department = departmentMapper.findById("18ec781fbefd727923b0d35740b177ab");
            System.out.println(department);
*/

//            List<Department> departmentList = departmentMapper.findAll();

            Department department = departmentMapper.findById("53e3803ebbf4f97968e0253e5ad4cc83");
            System.out.println(department.getName());
//            departmentList.forEach(System.out::println);
        }
    }
}
