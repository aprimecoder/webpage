package com.primecoder.webpage.spider.core.test;

import com.primecoder.webpage.spider.core.parser.ParserBloggerTagListPage;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;

/**
 * Created by primecoder on 2017/8/12.
 * E-mail : aprimecoder@gmail.com
 */
public class TestParserBloggerTagPage {

    private static final ParserBloggerTagListPage PARSER_BLOGGER_TAG_PAGE = ParserBloggerTagListPage.getInstance();

    public static void main(String[] args) {

        String log4jConfPath = System.getProperty("user.dir") + "/spider-core/src/main/java/log4j.properties";

        PropertyConfigurator.configure(log4jConfPath);

        File file = new File("E:\\webpage\\blogger\\loveincode\\tag-page.html");

        PARSER_BLOGGER_TAG_PAGE.parser(file,"loveincode");
    }
}
