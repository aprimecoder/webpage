package com.primecoder.webpage.spider.core.test;

import com.primecoder.webpage.spider.core.dao.BloggerDao;

/**
 * Created by primecoder on 2017/7/15.
 * E-mail : aprimecoder@gmail.com
 */
public class TestBloggerDao {


    public static void main(String[] args) {

        BloggerDao bloggerDao = BloggerDao.getInstance();

        String bloggerId = bloggerDao.getIdByName("Zihuatanejo");

        System.out.println(bloggerId);
    }
}
