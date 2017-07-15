package com.primecoder.webpage.spider.core.test;

import com.primecoder.webpage.spider.core.parser.ParserBloggerPage;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;

/**
 * Created by primecoder on 2017/7/8.
 * E-mail : aprimecoder@gmail.com
 */
public class TestParserBloggerPage {


    public static void main(String[] args) {

        String log4jConfPath = System.getProperty("user.dir") + "/spider-core/src/main/java/log4j.properties";

        PropertyConfigurator.configure(log4jConfPath);

        ParserBloggerPage parserBloggerPage = ParserBloggerPage.getInstance();

        File file = new File("E:\\webpage\\blogger\\espie\\home.html");

        parserBloggerPage.parser(file);
    }
}
