package com.primecoder.webpage.spider.core.service;

import com.primecoder.core.util.dir.DirMgr;
import com.primecoder.webpage.spider.core.dao.BloggerDao;
import com.primecoder.webpage.spider.core.dao.UrlDownloadedDao;
import com.primecoder.webpage.spider.core.download.HttpClientDownload;
import com.primecoder.webpage.spider.core.entity.BloggerEntity;
import com.primecoder.webpage.spider.core.entity.UrlDownloadedEntity;
import com.primecoder.webpage.spider.core.parser.ParserBloggerGuid;
import com.primecoder.webpage.spider.core.parser.ParserBloggerHomePage;
import com.primecoder.webpage.spider.core.parser.ParserBloggerPage;
import com.primecoder.webpage.spider.core.storage.Storage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

/**
 * Created by primecoder on 2017/7/8.
 * E-mail : aprimecoder@gmail.com
 */
public class BloggerService {


    private static BloggerService instance = null;

    public static final String BLOGGER_BASE_PATH = "E:\\webpage\\blogger\\";

    private static final Logger LOGGER = LoggerFactory.getLogger(BloggerService.class);

    private final HttpClientDownload httpClientDownload = new HttpClientDownload();

    private final Storage storage = new Storage();

    public static final ParserBloggerPage PARSER_BLOGGER_PAGE = ParserBloggerPage.getInstance();

    private static final ParserBloggerHomePage PARSER_BLOGGER_HOME_PAGE = ParserBloggerHomePage.getInstance();

    private static final ParserBloggerGuid PARSER_BLOGGER_GUID = ParserBloggerGuid.getInstance();

    private static final UrlDownloadedDao URL_DOWNLOADED_DAO = UrlDownloadedDao.getInstance();

    public static final BloggerDao BLOGGER_DAO = BloggerDao.getInstance();

    private BloggerService() {


    }

    public static synchronized BloggerService getInstance() {

        if (null == instance) {
            instance = new BloggerService();
        }

        return instance;
    }

    public String getBloggerIdByName(String bloggerName) {

        String dbId = getBloggerIdFromDB(bloggerName);

        if (null != dbId) {

            LOGGER.info("bloggerName : {} is already in DB,id : {}",bloggerName,dbId);

            return dbId;
        }

        String bloggerUrl = "http://www.cnblogs.com/" + bloggerName + "/";

        String content = httpClientDownload.download(bloggerUrl);

        String bloggerPath = BLOGGER_BASE_PATH + bloggerName;
        DirMgr.mkdir(bloggerPath);
        String bloggerHomePath = bloggerPath + "\\home.html";

        if (null != content) {
            storage.storage(bloggerHomePath,content);
        } else {

            return null;
        }

        //downloaded url into db
        putDownloadedIntoDB(bloggerName,bloggerUrl,bloggerHomePath,0);

        File homeFile = new File(bloggerHomePath);
        String pageDocumentUrl = PARSER_BLOGGER_PAGE.parser(homeFile);

        if (pageDocumentUrl == null) {

            return getBloggerIdByHomePage(bloggerName);
        }

        String documentContent = httpClientDownload.download(pageDocumentUrl);

        DirMgr.mkdir(bloggerPath);
        String bloggerDocumentPath = bloggerPath + "\\1.html";

        if (null != documentContent) {
            storage.storage(bloggerDocumentPath,documentContent);
        } else {

            return null;
        }

        putDownloadedIntoDB(bloggerName,pageDocumentUrl,bloggerDocumentPath,1);

        String bloggerId = PARSER_BLOGGER_GUID.parser(new File(bloggerDocumentPath));

        BloggerEntity bloggerEntity = new BloggerEntity();
        bloggerEntity.setBloggerId(bloggerId);
        bloggerEntity.setBloggerUrl(bloggerUrl);
        bloggerEntity.setBloggerName(bloggerName);

        BLOGGER_DAO.insert(bloggerEntity);

        return bloggerId;
    }

    public String getBloggerIdByHomePage(String bloggerName) {

        String homeUrl = "https://home.cnblogs.com/u/" + bloggerName;

        String content = httpClientDownload.download(homeUrl);

        String bloggerPath = BLOGGER_BASE_PATH + bloggerName;
        DirMgr.mkdir(bloggerPath);
        String bloggerHomePath = bloggerPath + "\\home.html";

        if (null != content) {
            storage.storage(bloggerHomePath,content);

            String bloggerId = PARSER_BLOGGER_HOME_PAGE.parser(new File(bloggerHomePath));
            String bloggerUrl = "http://www.cnblogs.com/" + bloggerName + "/";

            BloggerEntity bloggerEntity = new BloggerEntity();
            bloggerEntity.setBloggerId(bloggerId);
            bloggerEntity.setBloggerUrl(bloggerUrl);
            bloggerEntity.setBloggerName(bloggerName);

            BLOGGER_DAO.insert(bloggerEntity);

            return bloggerId;

        } else {

            return null;
        }
    }

    private String getBloggerIdFromDB(String bloggerName) {

        String bloggerId = BLOGGER_DAO.getIdByName(bloggerName);

        return bloggerId;
    }

    private void putDownloadedIntoDB(String bloggerName,String url,String filePath,int type) {

        UrlDownloadedEntity urlDownloadedEntity = new UrlDownloadedEntity();
        urlDownloadedEntity.setBloggerName(bloggerName);
        urlDownloadedEntity.setFilepath(filePath);
        urlDownloadedEntity.setUrl(url);
        urlDownloadedEntity.setType(type);

        URL_DOWNLOADED_DAO.insert(urlDownloadedEntity);
    }
}
