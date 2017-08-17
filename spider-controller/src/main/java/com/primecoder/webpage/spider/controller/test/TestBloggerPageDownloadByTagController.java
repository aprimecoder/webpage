package com.primecoder.webpage.spider.controller.test;

import com.primecoder.webpage.spider.controller.blogger.BloggerPageDownloadByTagController;
import com.primecoder.webpage.spider.core.dao.BloggerDao;
import org.apache.log4j.PropertyConfigurator;

/**
 * Created by primecoder on 2017/8/12.
 * E-mail : aprimecoder@gmail.com
 */
public class TestBloggerPageDownloadByTagController {


    private static final BloggerDao BLOGGER_DAO = BloggerDao.getInstance();

    public static void main(String[] args) {

        String log4jConfPath = "C:\\code\\webpage\\spider-core\\src\\main\\java\\log4j.properties";

        PropertyConfigurator.configure(log4jConfPath);


        BloggerPageDownloadByTagController bloggerPageDownloadByTagController = new BloggerPageDownloadByTagController();

        while (true) {

            String bloggerName = BLOGGER_DAO.getBloggerName();

            bloggerPageDownloadByTagController.start(bloggerName);
        }


    }
}
