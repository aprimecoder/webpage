package com.primecoder.webpage.spider.core.dao;

import com.primecoder.webpage.spider.core.entity.BloggerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by primecoder on 2017/7/8.
 * E-mail : aprimecoder@gmail.com
 */
public class BloggerDao {


    private static final Logger LOGGER = LoggerFactory.getLogger(BloggerDao.class);

    private static final CommonDao COMMON_DAO = CommonDao.getInstance();

    private static final DBConnection DB_CONNECTION = DBConnection.getInstance();

    private static BloggerDao instance = null;

    private BloggerDao() {

    }

    public static synchronized BloggerDao getInstance() {

        if (null == instance) {
            instance = new BloggerDao();
        }

        return instance;
    }

    public void insert(BloggerEntity bloggerEntity) {

        boolean exist = exist(bloggerEntity.getBloggerId());

        if (!exist) {

            String insertSql= "INSERT INTO blogger_info (blogger_id,blogger_name,blogger_url) " +
                    "VALUES('" + bloggerEntity.getBloggerId() + "','" + bloggerEntity.getBloggerName()
                    + "','" + bloggerEntity.getBloggerUrl() + "')";

            LOGGER.info("insert bloggerEntity sql : {}",insertSql);

            COMMON_DAO.executeInsert(insertSql);

        }

    }

    private boolean exist(String bloggerId) {

        String selectSql = "select count(1) cnt from blogger_info where blogger_id = '" + bloggerId + "'";

        return COMMON_DAO.exist(selectSql);

    }

    public String getIdByName(String bloggerName) {

        String sql = "select blogger_id from blogger_info where blogger_name = '" + bloggerName + "'";

        Statement stmt = null;

        Connection connection = DB_CONNECTION.get();

        try {

            stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            if (res.next()) {
                String bloggerId = res.getString("blogger_id");

                return bloggerId;
            }

        } catch (SQLException e) {

            LOGGER.error("execute sql : {} error : {}",sql,e.getMessage());

        } finally {

            if (null != stmt) {
                try {
                    stmt.close();
                } catch (SQLException e) {

                    LOGGER.error("execute sql : {} error : {}",sql,e.getMessage());
                }
            }

            DB_CONNECTION.put(connection);
        }

        return null;
    }

    public void bloggerHandled(String bloggerId) {

        String sql = "update blogger_info set is_handle = true where blogger_id = '" + bloggerId + "'";

        COMMON_DAO.executeInsert(sql);
    }

    public List<String> getBloggerIds(int number) {

        String sql = "select blogger_id bloggerId from blogger_info where is_handle = false LIMIT 0,500";

        Statement stmt = null;

        Connection connection = DB_CONNECTION.get();

        List<String> bloggerIds = new ArrayList<>();

        try {

            stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            while (res.next()) {
                String bloggerId = res.getString("bloggerId");
                bloggerIds.add(bloggerId);
            }

        } catch (SQLException e) {

            LOGGER.error("execute sql : {} error : {}",sql,e.getMessage());

        } finally {

            if (null != stmt) {
                try {
                    stmt.close();
                } catch (SQLException e) {

                    LOGGER.error("execute sql : {} error : {}",sql,e.getMessage());
                }
            }

            DB_CONNECTION.put(connection);
        }

        return bloggerIds;
    }

}
