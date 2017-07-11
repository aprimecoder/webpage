package com.primecoder.webpage.spider.core.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created by primecoder on 2017/7/8.
 * E-mail : aprimecoder@gmail.com
 */
public class ParserBloggerPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParserBloggerPage.class);

    private static ParserBloggerPage instance = null;

    private ParserBloggerPage() {


    }

    public static synchronized ParserBloggerPage getInstance() {

        if (null == instance) {
           instance = new ParserBloggerPage();
        }

        return instance;
    }

    public String parser(File file) {

        if (null == file) {

            LOGGER.error("blogger page to parser is null!");

            return null;
        }

        Document doc;

        try {
            doc = Jsoup.parse(file, "UTF-8", "");
            if (null != doc) {

                Elements pageClasses = doc.getElementsByClass("postTitle");
                int size = pageClasses.size();

                if (size > 0) {
                    String bloggerDocumentUrl =  pageClasses.get(0).childNode(1).attributes().get("href");

                    LOGGER.info("blogger : {} document url : {}",file.getAbsolutePath(),bloggerDocumentUrl);

                    return bloggerDocumentUrl;
                }
            }

        } catch (IOException e) {

            LOGGER.info("blogger : {} error : {}",file.getAbsolutePath(),e.getMessage());
        }

        return null;

    }


}
