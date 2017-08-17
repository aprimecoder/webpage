package com.primecoder.webpage.spider.controller.test;

import com.primecoder.webpage.spider.core.parser.ParserBloggerTagPage;

import java.io.File;

/**
 * Created by primecoder on 2017/8/12.
 * E-mail : aprimecoder@gmail.com
 */
public class TestParserBloggerTagPage {

    private static ParserBloggerTagPage parserBloggerTagPage = ParserBloggerTagPage.getInstance();

    public static void main(String[] args) {

        File file = new File("E:\\webpage\\blogger\\loveincode\\02 集合框架(源码)\\tag-page.html");

        parserBloggerTagPage.parser(file,"","",1,"");
    }
}
