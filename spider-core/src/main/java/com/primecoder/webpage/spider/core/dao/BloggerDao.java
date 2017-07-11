package com.primecoder.webpage.spider.core.dao;

import com.primecoder.webpage.spider.core.entity.BloggerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

}
