package com.primecoder.webpage.download.util;

import com.primecoder.webpage.download.domain.Blogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by primecoder on 2017/1/19.
 * E-mail : aprimecoder@126.com
 */
public class DBOperate {

    private final static DBOperate dbOperate = new DBOperate();

    private Connection connection = null;

    private static final DBConnectionMgr dbConnectionMgr = DBConnectionMgr.getInstance();

    private DBOperate() {

        createConnection();
    }

    public static synchronized DBOperate getInstance() {

        return dbOperate;
    }

    private void createConnection() {

        if (connection != null) {
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/world?serverTimezone=UTC", "root", "123456");
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertAchievedUrl(String url) {

        String sql = "INSERT INTO achievedurl (url) VALUES('" + url + "')";

        executeInsert(sql);
    }

    public int queryAchievedUrl(String url) {

        Connection connection1 = dbConnectionMgr.getConnection();

        String sql = "select count(1) cnt from achievedurl where url = '" + url + "'";

        Statement stmt = null; //创建声明
        ResultSet res = null;
        try {

            stmt = connection1.createStatement();
            res = stmt.executeQuery(sql);
            if (res.next()) {
                return res.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            dbConnectionMgr.release(connection1,stmt,res);
        }

        return 0;
    }

    private void executeInsert(String sql) {

        Connection connection1 = dbConnectionMgr.getConnection();

        Statement stmt = null;
        try {
            stmt = connection1.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("sql:" + sql);
            e.printStackTrace();
        } finally {

            dbConnectionMgr.release(connection1,stmt,null);

        }
    }

    public void insertBlogger(String blogger, String bloggerUrl) {

        String sql = "INSERT INTO blogger (blogger,blogger_url) VALUES('"
                + blogger + "','" + bloggerUrl + "')";

        executeInsert(sql);

    }

    public void updateMaxPageIndex(String blogger, int maxIndex) {

        String sql = "update blogger set page_index_cnt = " + maxIndex + " where blogger = '" + blogger + "'";

        executeInsert(sql);
    }

    public void insertUnspiderUrl(String url) {

        String sql = "insert into unspiderurl (url) values('" + url + "')";

        executeInsert(sql);
    }

    public List<String> getUnspiderUrlList() {

        List<String> unspiderUrlList = new ArrayList<String>();

        String sql = "select url from unspiderurl";

        Statement stmt = null;
        try {

            stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            while (res.next()) {
                String url = res.getString("url");
                unspiderUrlList.add(url);
            }

            return unspiderUrlList;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (null != stmt) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return unspiderUrlList;
    }

    public List<String> getAllBloggers(){

        List<String> bloggers = new ArrayList<String>();

        String sql = "select blogger from blogger";

        Statement stmt = null;
        try {

            stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            while (res.next()) {

                String blogger = res.getString("blogger");

                bloggers.add(blogger);

            }

            return bloggers;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (null != stmt) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return bloggers;
    }

    public List<Blogger> getBlogger() {

        List<Blogger> bloggers = new ArrayList<Blogger>();

        String sql = "select blogger,blogger_url,page_index_cnt,page_cnt from blogger where page_index_cnt != 0";

        Statement stmt = null;
        try {

            stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            while (res.next()) {

                String blogger = res.getString("blogger");
                String bloggerUrl = res.getString("blogger_url");
                int pageIndexCnt = res.getInt("page_index_cnt");
                int pageCnt = res.getInt("page_cnt");

                Blogger bloggerEntity = new Blogger();
                bloggerEntity.setBlogger(blogger);
                bloggerEntity.setBloggerUrl(bloggerUrl);
                bloggerEntity.setPageIndexCnt(pageIndexCnt);
                bloggerEntity.setPageCnt(pageCnt);
                bloggers.add(bloggerEntity);

            }

            return bloggers;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (null != stmt) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return bloggers;
    }

    public void insertUrl(String url, String blogger) {
        String sql = "insert into url (url,blogger_id) values('" + url + "','" + blogger + "')";
        executeInsert(sql);
    }

    public List<String> getUrl(String blogger) {

        String sql = "select url from url where blogger_id = '" + blogger + "'";
        List<String> urlList = new ArrayList<String>();

        Statement stmt = null;
        try {

            stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            while (res.next()) {

                String url = res.getString("url");

                urlList.add(url);

            }

            return urlList;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (null != stmt) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return urlList;
    }
}
