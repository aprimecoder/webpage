package com.primecoder.webpage.spider.controller.test;

import com.primecoder.webpage.spider.controller.homepage.HomePageParser;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;

/**
 * Created by primecoder on 2017/5/6.
 * E-mail : aprimecoder@gmail.com
 */
public class TestHomePageParser {

    public static void main(String[] args){

        String log4jConfPath = System.getProperty("user.dir") + "/spider-core/src/main/java/log4j.properties";

        PropertyConfigurator.configure(log4jConfPath);

        HomePageParser homePageParser = new HomePageParser();

        homePageParser.start();
//
//        File file = new File("E:\\webpage\\homepage\\2017-7-8\\1.html");
//        homePageParser.parser(file);
    }

}
