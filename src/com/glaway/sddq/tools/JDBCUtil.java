package com.glaway.sddq.tools;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * JDBC工具类
 * @author Miller
 * @date 2019-06-12 17:12
 */
public class JDBCUtil {

    static Properties prop = null;

    static {//加载JDBCUtil类时调用
        prop = new Properties();
        try{
            prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("jpo.properties"));
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        System.out.println(prop.getProperty("c3p0.jdbcUrl"));
        Connection con = getOrclConn();

    }

    /**
     * 获取oracle数据库连接
     * @return conn
     */
    public static Connection getOrclConn(){
        try {
                 Class.forName("oracle.jdbc.driver.OracleDriver");
                 return DriverManager.getConnection(prop.getProperty("c3p0.jdbcUrl"),
                         prop.getProperty("c3p0.user"),prop.getProperty("c3p0.password"));
             } catch (Exception e) {
                 e.printStackTrace();
                 return null;
             }
    }

    /**
     * 关闭数据库连接
     * @param rs
     * @param st
     * @param conn
     */
    public static void close(ResultSet rs, Statement st, Connection conn){

         try {
            if (rs!=null) {
                rs.close();
            }
         } catch (SQLException e) {
             e.printStackTrace();
         }

         try {
            if (st!=null) {
                st.close();
            }
         } catch (SQLException e) {
            e.printStackTrace();
         }

         try {
            if (conn!=null) {
                conn.close();
            }
         } catch (SQLException e) {
            e.printStackTrace();
         }

    }

}
