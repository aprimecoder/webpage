package com.primecoder.webpage.spider.core.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Created by primecoder on 2017/7/8.
 * E-mail : aprimecoder@gmail.com
 */
public class RelationDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(RelationDao.class);

    private static final CommonDao COMMON_DAO = CommonDao.getInstance();

    private static final DBConnection DB_CONNECTION = DBConnection.getInstance();

    private static RelationDao instance = null;

    private RelationDao() {

    }

    public static synchronized RelationDao getInstance() {

        if (null == instance) {
            instance = new RelationDao();
        }

        return instance;
    }

    public boolean exist(String followeeId,String followerId) {

        String sql = "select count(1) cnt from blogger_relation where followee_id = '"
                + followeeId + "' and follower_id = '" + followerId + "'";

        return COMMON_DAO.exist(sql);
    }

    public void insert(String followeeId,String followerId,boolean relation) {

        String id = UUID.randomUUID().toString();

        String insertSql= "INSERT INTO blogger_relation (id,followee_id,follower_id,is_relate) " +
                "VALUES('" + id + "','" + followeeId + "','" + followerId
                + "'," + relation + ")";

        COMMON_DAO.executeInsert(insertSql);
    }

    public void update(String followeeId,String followerId,boolean relation) {

        String updateSql= "update blogger_relation set is_relate = " + relation + " where followee_id = '"
                + followeeId + "' and follower_id = '" + followerId + "'";

        COMMON_DAO.executeInsert(updateSql);
    }
}
