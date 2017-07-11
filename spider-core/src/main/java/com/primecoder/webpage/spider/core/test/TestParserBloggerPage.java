package com.primecoder.webpage.spider.core.test;

import com.primecoder.webpage.spider.core.parser.ParserBloggerPage;

import java.io.File;

/**
 * Created by primecoder on 2017/7/8.
 * E-mail : aprimecoder@gmail.com
 */
public class TestParserBloggerPage {


    public static void main(String[] args) {

        ParserBloggerPage parserBloggerPage = ParserBloggerPage.getInstance();

        File file = new File("E:\\webpage\\temp\\test.html");

        parserBloggerPage.parser(file);
    }
}
