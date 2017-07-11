package com.primecoder.webpage.spider.core.service;

import com.primecoder.core.util.dir.DirMgr;
import com.primecoder.webpage.spider.core.dao.BloggerDao;
import com.primecoder.webpage.spider.core.download.HttpClientDownload;
import com.primecoder.webpage.spider.core.entity.BloggerEntity;
import com.primecoder.webpage.spider.core.parser.ParserBloggerGuid;
import com.primecoder.webpage.spider.core.parser.ParserBloggerPage;
import com.primecoder.webpage.spider.core.storage.Storage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

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

    private static final ParserBloggerGuid PARSER_BLOGGER_GUID = ParserBloggerGuid.getInstance();

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

        String bloggerUrl = "http://www.cnblogs.com/" + bloggerName + "/";

        String content = httpClientDownload.download(bloggerUrl);

        String bloggerPath = BLOGGER_BASE_PATH + bloggerName;
        DirMgr.mkdir(bloggerPath);
        String bloggerHomePath = bloggerPath + "\\home.html";

        if (null != content) {
            storage.storage(bloggerHomePath,content);
        }

        File homeFile = new File(bloggerHomePath);
        String pageDocumentUrl = PARSER_BLOGGER_PAGE.parser(homeFile);
        String documentContent = httpClientDownload.download(pageDocumentUrl);

        DirMgr.mkdir(bloggerPath);
        String bloggerDocumentPath = bloggerPath + "\\1.html";

        if (null != documentContent) {
            storage.storage(bloggerDocumentPath,documentContent);
        }

        String bloggerId = PARSER_BLOGGER_GUID.parser(new File(bloggerDocumentPath));

        BloggerEntity bloggerEntity = new BloggerEntity();
        bloggerEntity.setBloggerId(bloggerId);
        bloggerEntity.setBloggerUrl(bloggerUrl);
        bloggerEntity.setBloggerName(bloggerName);

        BLOGGER_DAO.insert(bloggerEntity);

        return bloggerId;
    }
}
