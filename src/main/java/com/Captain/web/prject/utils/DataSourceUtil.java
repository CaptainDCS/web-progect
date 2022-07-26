package com.Captain.web.prject.utils;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * projectName:day10_web2
 * author:dcs
 * time:2021/5/28 17:23
 * description:
 */
public class DataSourceUtil {
    private static DruidDataSource dataSource=null;

    private static ThreadLocal<Connection> threadLocal=new ThreadLocal<Connection>();

    static{
        try {
            Properties properties = new Properties();
            InputStream is = DataSourceUtil.class.getClassLoader().getResourceAsStream("druid.properties");
            properties.load(is);
            dataSource= (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            System.out.println("初始化连接池失败");
        }
    }
    public static DataSource getDataSource(){
        return dataSource;
    }

    //事务处理
    public static Connection getConnection(){
        Connection connection = threadLocal.get();
        try {
            if (connection == null) {
                connection=dataSource.getConnection();
                threadLocal.set(connection);
            }
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void begin()throws Exception{
        Connection connection = getConnection();
        if (connection != null) {
            connection.setAutoCommit(false);
        }
    }
    public static void commit()throws Exception{
        Connection connection = getConnection();
        if (connection != null) {
            connection.commit();
        }
    }
    public static void rollback()throws Exception{
        Connection connection = getConnection();
        if (connection != null) {
            connection.rollback();
        }
    }
    public static void close()throws Exception{
        Connection connection = getConnection();
        if (connection != null) {
            connection.close();
            threadLocal.remove();
        }
    }

}
