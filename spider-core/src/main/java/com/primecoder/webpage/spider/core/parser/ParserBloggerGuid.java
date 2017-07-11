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
 * Created by primecoder on 2017/7/8.
 * E-mail : aprimecoder@gmail.com
 */
public class ParserBloggerGuid {

    public static final Logger LOGGER= LoggerFactory.getLogger(ParserBloggerGuid.class);

    private static ParserBloggerGuid instance = null;

    private ParserBloggerGuid() {

    }

    public static synchronized ParserBloggerGuid getInstance() {

        if (null == instance) {
            instance = new ParserBloggerGuid();
        }

        return instance;
    }

    public String parser(File file) {

        if (null == file) {
            LOGGER.info("parser blogger guid file is null!");

            return null;
        }

        Document doc;

        String blogUserGuid = null;

        try {
            doc = Jsoup.parse(file, "UTF-8", "");

            Elements es = doc.getElementsByTag("script");
            for (Element e : es) {

                if (null != e.childNodes() && e.childNodes().size() != 0) {
                    String data = e.childNode(0).attr("data");
                    if (data.contains("cb_blogUserGuid")) {
                        String[] arr = data.split(",");
                        for (String str : arr) {
                            if (str.contains("cb_blogUserGuid")) {
                                String s = str.split("=")[1];
                                blogUserGuid = s.substring(1,s.length() - 1);
                                break;
                            }
                        }
                    }
                }

                if (blogUserGuid != null) {
                    break;
                }

            }
        } catch (IOException e) {

            LOGGER.info("parser file : {} error : {}",file.getName(),e.getMessage());
        }

        LOGGER.info("parser file : {} success,get user guid : {}",file.getName(),blogUserGuid);

        return blogUserGuid;

    }
}
