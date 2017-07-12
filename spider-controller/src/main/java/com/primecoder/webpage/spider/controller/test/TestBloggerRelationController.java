package com.primecoder.webpage.spider.controller.test;

import com.primecoder.webpage.spider.controller.relation.BloggerRelationController;
import org.apache.log4j.PropertyConfigurator;

/**
 * Created by primecoder on 2017/7/11.
 * E-mail : aprimecoder@gmail.com
 */
public class TestBloggerRelationController {

    public static void main(String[] args) throws InterruptedException {

        String log4jConfPath = System.getProperty("user.dir") + "/spider-core/src/main/java/log4j.properties";

        PropertyConfigurator.configure(log4jConfPath);

        BloggerRelationController bloggerRelationController = new BloggerRelationController();

        bloggerRelationController.start();
    }
}
