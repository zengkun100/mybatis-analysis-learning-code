package com.linkedbear.mybatis.app;

import com.linkedbear.mybatis.entity.Department;
import com.linkedbear.mybatis.entity.User;
import com.linkedbear.mybatis.mapper.DepartmentMapper;
import com.linkedbear.mybatis.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

public class Level2ReadonlyApplication {
    
    public static void main(String[] args) throws Exception {
        InputStream xml = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(xml);
        SqlSession sqlSession = sqlSessionFactory.openSession();
    
        DepartmentMapper departmentMapper = sqlSession.getMapper(DepartmentMapper.class);
        System.out.println("sqlSession执行findAll......");
        List<Department> departmentList = departmentMapper.findAll();
    
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> userList = userMapper.findAll();
    
        sqlSession.close();
    
        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        DepartmentMapper departmentMapper2 = sqlSession2.getMapper(DepartmentMapper.class);
        System.out.println("sqlSession2执行findAll......");
        List<Department> departmentList2 = departmentMapper2.findAll();
    
        UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);
        List<User> userList2 = userMapper2.findAll();
    
        sqlSession2.close();
    }
}
