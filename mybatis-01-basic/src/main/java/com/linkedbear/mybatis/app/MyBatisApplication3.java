package com.linkedbear.mybatis.app;

import com.linkedbear.mybatis.entity.Department;
import com.linkedbear.mybatis.mapper.DepartmentMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.io.PipedReader;
import java.util.List;

public class MyBatisApplication3 {
    
    public static void main(String[] args) throws Exception {
        InputStream xml = Resources.getResourceAsStream("mybatis-config-2.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(xml);

        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
        DepartmentMapper departmentMapper = sqlSession.getMapper(DepartmentMapper.class);
        Department department = departmentMapper.findById("18ec781fbefd727923b0d35740b177ab");
        System.out.println(department);

        System.out.println("------------------------");

        department.setName("基础架构开发部");
        departmentMapper.update(department);

//        System.out.println("------------------------");

        Department productDepartment = new Department();
        productDepartment.setName("产品团队");
        productDepartment.setId("b669bc18044411efa5ff0242ac130002");
        productDepartment.setTel("12345678");
        departmentMapper.insert(productDepartment);

        List<Department> departmentList = departmentMapper.findAll();
        departmentList.forEach(System.out::println);
    }


//        System.out.println(department);
    }
}
