package com.primecoder.webpage.spider.core.test;

import com.primecoder.webpage.spider.core.parser.ParserBloggerGuid;

import java.io.File;

/**
 * Created by primecoder on 2017/7/8.
 * E-mail : aprimecoder@gmail.com
 */
public class TestParserBloggerGuid {


    public static void main(String[] args ) {

        ParserBloggerGuid parserBloggerGuid = ParserBloggerGuid.getInstance();

        File file = new File("E:\\webpage\\temp\\charliechu-document.html");

        String blogUserGuid = parserBloggerGuid.parser(file);

        System.out.println(blogUserGuid);
    }
}
