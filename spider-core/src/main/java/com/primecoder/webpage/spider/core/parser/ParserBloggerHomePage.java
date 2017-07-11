package com.primecoder.webpage.spider.core.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created by primecoder on 2017/7/11.
 * E-mail : aprimecoder@gmail.com
 */
public class ParserBloggerHomePage {

    public static final Logger LOGGER = LoggerFactory.getLogger(ParserBloggerHomePage.class);

    private static ParserBloggerHomePage instance = null;

    private ParserBloggerHomePage() {


    }

    public static synchronized ParserBloggerHomePage getInstance() {

        if (null == instance) {
            instance = new ParserBloggerHomePage();
        }

        return instance;
    }

    public String parser(File file) {

        if (null == file) {
            return null;
        }

        Document doc;

        String blogUserGuid = null;

        try {

            doc = Jsoup.parse(file, "UTF-8", "");
            Elements es = doc.getElementsByTag("script");

            String text = es.get(1).childNode(0).attr("data");
            int userIndex = text.indexOf("=") + 3;
            blogUserGuid = text.substring(userIndex,userIndex+ 36);

        } catch (IOException e) {

            LOGGER.error("parser blogger home page file : {} error msg : {}",file.getAbsolutePath(),e.getMessage());
        }

        LOGGER.info("parser file : {} bloggerId : {}",file.getAbsolutePath(),blogUserGuid);

        return blogUserGuid;
    }
}
