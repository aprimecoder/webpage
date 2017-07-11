package com.primecoder.webpage.spider.controller.test;

import com.primecoder.webpage.spider.controller.homepage.HomePageDownloadController;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

/**
 * Created by primecoder on 2017/5/6.
 * E-mail : aprimecoder@gmail.com
 */
public class TestHomePageDownloadController {

    public static void main(String[] args) {

        String log4jConfPath = System.getProperty("user.dir") + "/spider-core/src/main/java/log4j.properties";

        PropertyConfigurator.configure(log4jConfPath);

        BasicConfigurator.configure();

        HomePageDownloadController homePageDownloadController = new HomePageDownloadController();

        homePageDownloadController.start();
    }
}
