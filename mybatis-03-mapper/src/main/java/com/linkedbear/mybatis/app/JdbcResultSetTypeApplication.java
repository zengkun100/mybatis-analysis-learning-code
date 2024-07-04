package com.linkedbear.mybatis.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JdbcResultSetTypeApplication {
    
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mybatis?autoReconnect=true&useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&zeroDateTimeBehavior=convertToNull&serverTimezone=UTC", "root", "0505oxox");
//        PreparedStatement ps = connection.prepareStatement("select * from tbl_department");
        PreparedStatement ps = connection.prepareStatement("select * from tbl_department",
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = ps.executeQuery();
        
        // 遍历游标向下迭代
        while (resultSet.next()) {
            System.out.println(resultSet.getString("name"));
        }
        
        // 遍历游标向上迭代
        while (resultSet.previous()) {
            System.out.println("倒序 --- " + resultSet.getString("name"));
        }
        
        resultSet.close();
        ps.close();
        connection.close();
    }
}
