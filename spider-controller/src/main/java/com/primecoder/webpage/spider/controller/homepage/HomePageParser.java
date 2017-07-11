package com.primecoder.webpage.spider.controller.homepage;

import com.primecoder.core.util.date.CurrentDate;
import com.primecoder.core.util.dir.DirMgr;
import com.primecoder.webpage.spider.controller.blogger.BloggerMgr;
import com.primecoder.webpage.spider.core.dao.BloggerDao;
import com.primecoder.webpage.spider.core.download.HttpClientDownload;
import com.primecoder.webpage.spider.core.entity.BloggerEntity;
import com.primecoder.webpage.spider.core.parser.ParserBloggerGuid;
import com.primecoder.webpage.spider.core.storage.Storage;
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

    public static final String BLOGGER_BASE_PATH = "E:\\webpage\\blogger\\";

    private final HttpClientDownload httpClientDownload = new HttpClientDownload();

    private final Storage storage = new Storage();

    private static final ParserBloggerGuid PARSER_BLOGGER_GUID = ParserBloggerGuid.getInstance();

    private static final BloggerDao BLOGGER_DAO = BloggerDao.getInstance();

    public void start() {

        File homePagePath = new File(HomePageStoragePath.HOME_PAGE_STORAGE_BASE_PATH
                + CurrentDate.getDateString());

        File[] homePages = homePagePath.listFiles();

        for (File homePage : homePages) {

            Document doc;
            int i = 0;
            try {
                doc = Jsoup.parse(homePage, "UTF-8", "");

                if (null != doc) {

                    Elements bloggerClasses = doc.getElementsByClass("post_item_body");

                    int size = bloggerClasses.size();

                    for (i = 0;i < size;i++) {

                        Element bloggerClass = bloggerClasses.get(i);

                        String documentUrl = bloggerClass.childNodes().get(1).childNode(0).attr("href");
                        String bloggerUrl = bloggerClass.childNodes().get(5).childNode(1).attr("href");

                        String[] strs = bloggerUrl.split("/");
                        String bloggerName = strs[strs.length - 1];

                        String bloggerPath = BLOGGER_BASE_PATH + bloggerName;

                        DirMgr.mkdir(bloggerPath);

                        String bloggerDocumentPath = bloggerPath + "\\1.html";

                        String content = httpClientDownload.download(documentUrl);
                        if (null == content) {
                            continue;
                        } else {
                            storage.storage(bloggerDocumentPath,content);
                        }

                        File file = new File(bloggerDocumentPath);
                        String bloggerGuid = PARSER_BLOGGER_GUID.parser(file);
                        BloggerEntity bloggerEntity = new BloggerEntity();
                        bloggerEntity.setBloggerName(bloggerName);
                        bloggerEntity.setBloggerUrl(bloggerUrl);
                        bloggerEntity.setBloggerId(bloggerGuid);

                        BLOGGER_DAO.insert(bloggerEntity);
                    }
                }

            } catch (IOException e) {

                LOGGER.error("can not get file:{} to parser,msg:{}",homePage.getAbsolutePath(),e.getMessage());

            } catch (Exception e) {

                LOGGER.error("parser home page : {} error,i : {} ",homePage.getAbsolutePath(),i);
            }

        }
    }

    public void parser(File homePage) {

        Document doc;

        int i;

        try {
            doc = Jsoup.parse(homePage, "UTF-8", "");

            if (null != doc) {

                Elements bloggerClasses = doc.getElementsByClass("post_item_body");

                int size = bloggerClasses.size();

                for (i = 0;i < size;i++) {

                    System.out.println(i);

                    Element bloggerClass = bloggerClasses.get(i);
                    String documentUrl = bloggerClass.childNodes().get(1).childNode(0).attr("href");
                    String bloggerUrl = bloggerClass.childNodes().get(5).childNode(1).attr("href");
                }

            }
        } catch (IOException e) {


        } catch (Exception e) {


        }
    }
}
