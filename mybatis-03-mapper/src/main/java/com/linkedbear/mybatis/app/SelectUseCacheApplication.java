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
        // 缓存到2级之后，即便是一个新的SQL Session，也能hit
        DepartmentMapper departmentMapper2 = sqlSession2.getMapper(DepartmentMapper.class);
        Department department3 = departmentMapper2.findById("18ec781fbefd727923b0d35740b177ab");
        // 但是，从2级缓存查回来的department，不是同一个对象了。是需要反序列化的
        System.out.println("department2 == department3 : " + (department2 == department3));
        departmentMapper2.findAll();

        UserMapper userMapper = sqlSession2.getMapper(UserMapper.class);
        // flushCache 会清除所有的一级缓存，以及本 namespace 下的二级缓存。如果不在这里做一次flush的话，后面的findAll操作也会命中cache，并不会发起真的SQL
//        userMapper.cleanCache();
        System.out.println("==================cleanCache====================");

        // 因为department的2级缓存还在，所以可以命中，没有什么SQL发起
        Department department4 = departmentMapper2.findById("18ec781fbefd727923b0d35740b177ab");
        // 同样的，从2级缓存查回来的department，不是同一个对象了。是需要反序列化的
        System.out.println("department3 == department4 : " + (department3 == department4));
        departmentMapper2.findAll();
    
        sqlSession2.close();
    }
}
