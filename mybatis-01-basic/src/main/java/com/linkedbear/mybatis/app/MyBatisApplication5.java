package com.linkedbear.mybatis.app;

import com.linkedbear.mybatis.entity.User;
import com.linkedbear.mybatis.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

public class MyBatisApplication5 {
    
    public static void main(String[] args) throws Exception {
        InputStream xml = Resources.getResourceAsStream("mybatis-config-3.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(xml);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
//            List<User> userList = userMapper.findAllLazy();
//            userList.forEach(System.out::println);

            List<User> userList = userMapper.findAllByDepartmentId("18ec781fbefd727923b0d35740b177ab");
            userList.forEach(System.out::println);
        }
    }
}
