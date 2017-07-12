package com.primecoder.webpage.spider.controller.relation;

import com.primecoder.webpage.spider.core.dao.BloggerDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by primecoder on 2017/7/12.
 * E-mail : aprimecoder@gmail.com
 */
public class QueueMgr {

    public static final BloggerDao BLOGGER_DAO = BloggerDao.getInstance();

    public static final Logger LOGGER = LoggerFactory.getLogger(QueueMgr.class);

    private static QueueMgr instance = null;

    private QueueMgr() {

    }

    public static synchronized QueueMgr getInstance() {

        if (null == instance) {
            instance = new QueueMgr();
        }

        return instance;
    }

    public static final Queue<String> queue = new LinkedList<>();

    public void put(String bloggerId) {

        queue.add(bloggerId);
    }

    public String get() {

        String bloggerId =  queue.poll();

        if (null == bloggerId) {
            LOGGER.info("queue bloggerId is null,please get from database!");

            putBloggerIdsIntoQueue();

            bloggerId = queue.poll();
        }

        LOGGER.info("get bloggerId : {} from queue!",bloggerId);

        return bloggerId;
    }

    private void putBloggerIdsIntoQueue() {

        List<String> bloggerIds = BLOGGER_DAO.getBloggerIds(500);
        for (String bloggerId : bloggerIds) {
            put(bloggerId);
        }
    }
}
