package com.primecoder.webpage.spider.core.dao;

import com.primecoder.webpage.spider.core.entity.BloggerTagEntity;

/**
 * Created by primecoder on 2017/8/12.
 * E-mail : aprimecoder@gmail.com
 */
public class BloggerTagDao {

    private static BloggerTagDao instance = null;

    private static final CommonDao COMMON_DAO = CommonDao.getInstance();

    private BloggerTagDao() {

    }

    public static synchronized BloggerTagDao getInstance() {

        if (null == instance) {
            instance = new BloggerTagDao();
        }

        return instance;
    }

    public void insert(BloggerTagEntity bloggerTagEntity) {

        String sql = "insert into blogger_tag (blogger_name,tag_name,tag_count,tag_url,tag_id,tag_type) values "
                + "('" + bloggerTagEntity.getBloggerName() + "','" + bloggerTagEntity.getTagName()
                + "'," + bloggerTagEntity.getTagCount() + ",'" + bloggerTagEntity.getTagUrl()
                + "','" + bloggerTagEntity.getTagId()
                + "'," + bloggerTagEntity.getTagType() + ")";

        COMMON_DAO.executeInsert(sql);

    }
}
