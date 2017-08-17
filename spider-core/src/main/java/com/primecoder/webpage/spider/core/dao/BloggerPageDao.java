package com.primecoder.webpage.spider.core.dao;

import com.primecoder.webpage.spider.core.entity.BloggerPageEntity;

/**
 * Created by primecoder on 2017/8/12.
 * E-mail : aprimecoder@gmail.com
 */
public class BloggerPageDao {

    private static BloggerPageDao instance = null;

    private static final CommonDao COMMON_DAO = CommonDao.getInstance();

    private BloggerPageDao() {

    }

    public static synchronized BloggerPageDao getInstance() {

        if (null == instance) {
            instance = new BloggerPageDao();
        }

        return instance;
    }

    public void insert(BloggerPageEntity bloggerPageEntity) {

        String sql = "insert into blogger_page_info (page_id,blogger_name,read_cnt,comment_cnt,recommend_cnt,tag_id,blogger_page_url) values "
                + "('" + bloggerPageEntity.getPageId() + "','" + bloggerPageEntity.getBloggerName()
                + "'," + bloggerPageEntity.getReadCount() + "," + bloggerPageEntity.getCommentCount()
                + "," + bloggerPageEntity.getRecommendCount() + ",'" + bloggerPageEntity.getTagId()
                + "','" + bloggerPageEntity.getBloggerPageUrl() +  "')";

        COMMON_DAO.executeInsert(sql);
    }

    public boolean existByUrl(String url) {

        String sql = "select count(1) cnt from blogger_page_info where blogger_page_url = '" + url + "'";
        return COMMON_DAO.exist(sql);
    }
}
