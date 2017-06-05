package com.primecoder.webpage.spider.controller.homepage;

import com.primecoder.core.util.date.CurrentDate;
import com.primecoder.webpage.spider.controller.blogger.BloggerMgr;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created by primecoder on 2017/5/6.
 * E-mail : aprimecoder@gmail.com
 */
public class HomePageParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomePageParser.class);

    private final BloggerMgr bloggerMgr = new BloggerMgr();

    public void start() {

        File homePagePath = new File(HomePageStoragePath.HOME_PAGE_STORAGE_BASE_PATH
                + CurrentDate.getDateString());

        File[] homePages = homePagePath.listFiles();

        for (File homePage : homePages) {

            Document doc = null;
            try {
                doc = Jsoup.parse(homePage, "UTF-8", "");

                if (null != doc) {

                    Elements bloggerClasses = doc.getElementsByClass("post_item_body");

                    for (Element bloggerClass : bloggerClasses) {

                        String documentUrl = bloggerClass.childNodes().get(1).childNode(0).attr("href");
                        String bloggerUrl = bloggerClass.childNodes().get(3).childNode(1).attr("href");

                        bloggerMgr.getBloggerEntity(documentUrl,bloggerUrl);
                    }
                }

            } catch (IOException e) {

                LOGGER.error("can not get file:{} to parser,msg:{}",homePage.getAbsolutePath(),e.getMessage());

            } catch (Exception e) {

                LOGGER.error("can not get file:{} to parser,msg:{}",homePage.getAbsolutePath(),e.getMessage());

            }

        }
    }
}
