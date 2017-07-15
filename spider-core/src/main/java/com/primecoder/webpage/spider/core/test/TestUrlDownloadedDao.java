package com.primecoder.webpage.spider.core.test;

import com.primecoder.webpage.spider.core.dao.UrlDownloadedDao;
import com.primecoder.webpage.spider.core.entity.UrlDownloadedEntity;

import java.util.List;

/**
 * Created by primecoder on 2017/7/15.
 * E-mail : aprimecoder@gmail.com
 */
public class TestUrlDownloadedDao {

    public static void main(String[] args) {

        UrlDownloadedDao urlDownloadedDao = UrlDownloadedDao.getInstance();

        UrlDownloadedEntity urlDownloadedEntity = new UrlDownloadedEntity();

        urlDownloadedEntity.setBloggerName("test001");
        urlDownloadedEntity.setUrl("www.test001.com");
        urlDownloadedEntity.setFilepath("E:/test001");
        urlDownloadedEntity.setType(0);

        //urlDownloadedDao.insert(urlDownloadedEntity);

        List<UrlDownloadedEntity> urlDownloadedEntities = urlDownloadedDao.getByBloggerName("test001");

        System.out.println("query!");
    }
}
