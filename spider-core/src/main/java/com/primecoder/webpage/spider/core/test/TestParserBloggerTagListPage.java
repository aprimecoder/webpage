package com.primecoder.webpage.spider.core.test;

import com.primecoder.webpage.spider.core.parser.ParserBloggerTagListPage;
import org.apache.log4j.PropertyConfigurator;

import java.util.ArrayList;

/**
 * Created by primecoder on 2017/8/14.
 * E-mail : aprimecoder@gmail.com
 */
public class TestParserBloggerTagListPage {


    private static final ParserBloggerTagListPage PARSER_BLOGGER_TAG_LIST_PAGE = ParserBloggerTagListPage.getInstance();

    public static void main(String[] args) {

        String log4jConfPath = System.getProperty("user.dir") + "/spider-core/src/main/java/log4j.properties";

        PropertyConfigurator.configure(log4jConfPath);

        //PARSER_BLOGGER_TAG_LIST_PAGE.achieveTagList("M-D-Luffy",new ArrayList<>());
    }
}
