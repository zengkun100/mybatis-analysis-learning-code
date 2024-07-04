package com.linkedbear.mybatis.app;

import com.linkedbear.mybatis.entity.Department;
import com.linkedbear.mybatis.mapper.DepartmentMapper;
import com.linkedbear.mybatis.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

public class SelectUseCacheApplication {
    
    public static void main(String[] args) throws Exception {
        InputStream xml = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(xml);
    
        SqlSession sqlSession = sqlSessionFactory.openSession();

        DepartmentMapper departmentMapper = sqlSession.getMapper(DepartmentMapper.class);
        Department department = departmentMapper.findById("18ec781fbefd727923b0d35740b177ab");
        System.out.println(department);

        // 命中了mybatis的一级缓存，没有SQL真正的发起
        Department department2 = departmentMapper.findById("18ec781fbefd727923b0d35740b177ab");
        // 这里是同一个对象
        System.out.println("department == department2 : " + (department == department2));
        // SqlSession 关闭，一级缓存持久化到二级缓存。
        sqlSession.close();

        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        DepartmentMapper departmentMapper2 = sqlSession2.getMapper(DepartmentMapper.class);
        Department department3 = departmentMapper2.findById("18ec781fbefd727923b0d35740b177ab");
        // SqlSession查回来的department，不是同一个对象了
        System.out.println("department2 == department3 : " + (department2 == department3));
        departmentMapper2.findAll();

        UserMapper userMapper = sqlSession2.getMapper(UserMapper.class);
        // flushCache 会清除全局一级缓存，以及本 namespace 下的二级缓存。
        userMapper.cleanCache();
        System.out.println("==================cleanCache====================");
        
        Department department4 = departmentMapper2.findById("18ec781fbefd727923b0d35740b177ab");
        // 这里竟然是false，因为mybatis的缓存是二级缓存，二级缓存是持久化的，所以这里hit了
        System.out.println("department3 == department4 : " + (department3 == department4));
        departmentMapper2.findAll();
    
        sqlSession2.close();
    }
}
