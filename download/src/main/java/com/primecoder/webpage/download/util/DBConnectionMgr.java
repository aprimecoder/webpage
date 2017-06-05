package com.primecoder.webpage.download.util;

import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Created by primecoder on 2017/1/26.
 * E-mail : aprimecoder@126.com
 */
public class DBConnectionMgr {

    private static final DBConnectionMgr dbConnectionMgr = new DBConnectionMgr();

    private static Properties properties = new Properties();

    private static DataSource dataSource;

    private DBConnectionMgr(){

    }

    public static synchronized DBConnectionMgr getInstance(){

        return dbConnectionMgr;
    }

    static{
        try{
            FileInputStream is = new FileInputStream("C:\\code\\download\\src\\main\\resources\\dhcp.properties");
            properties.load(is);
        }catch(IOException e){
            e.printStackTrace();
        }

        try{
            dataSource = BasicDataSourceFactory.createDataSource(properties);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void  main(String[] args) {

        DBConnectionMgr dbConnectionMgr = DBConnectionMgr.getInstance();
        Connection conn = dbConnectionMgr.getConnection();
        if (null != conn) {
            System.out.println(conn);
        }
    }

    public Connection getConnection(){
        Connection connection = null;
        try{
            connection = dataSource.getConnection();
        }catch(SQLException e){
            e.printStackTrace();
        }

        return connection;
    }

    public void release(Connection conn, Statement st, ResultSet rs){
        if(rs!=null){
            try{
                //关闭存储查询结果的ResultSet对象
                rs.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
            rs = null;
        }
        if(st!=null){
            try{
                //关闭负责执行SQL命令的Statement对象
                st.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(conn!=null){
            try{
                //将Connection连接对象还给数据库连接池
                conn.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
