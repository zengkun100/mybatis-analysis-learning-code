package com.linkedbear.mybatis.app;

import com.linkedbear.mybatis.entity.Department;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

public class MyBatisApplication1 {
    
    public static void main(String[] args) throws Exception {
        InputStream xml = Resources.getResourceAsStream("mybatis-config-1.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(xml);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            // 用selectList这种写法，要写完整的namespace+id
            List<Department> departmentList = sqlSession.selectList("com.linkedbear.mybatis.mapper.DepartmentMapper.findAll");
            departmentList.forEach(System.out::println);
        }
    }
}
