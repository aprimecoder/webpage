package com.primecoder.webpage.spider.core.test;

import com.primecoder.webpage.spider.core.parser.ParserBloggerHomePage;

import java.io.File;

/**
 * Created by primecoder on 2017/7/11.
 * E-mail : aprimecoder@gmail.com
 */
public class TestParserBloggerHomePage {

    public static void main(String[] args) {

        ParserBloggerHomePage parserBloggerHomePage = ParserBloggerHomePage.getInstance();

        File file = new File("E:\\webpage\\blogger\\969534\\home.html");

        parserBloggerHomePage.parser(file);
    }
}
