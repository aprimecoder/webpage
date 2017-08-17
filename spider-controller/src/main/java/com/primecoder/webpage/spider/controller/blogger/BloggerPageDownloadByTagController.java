package com.primecoder.webpage.spider.controller.blogger;

import com.primecoder.core.util.dir.DirMgr;
import com.primecoder.webpage.spider.core.dao.BloggerDao;
import com.primecoder.webpage.spider.core.dao.UrlDownloadedDao;
import com.primecoder.webpage.spider.core.download.HttpClientDownload;
import com.primecoder.webpage.spider.core.entity.BloggerTagEntity;
import com.primecoder.webpage.spider.core.entity.UrlDownloadedEntity;
import com.primecoder.webpage.spider.core.parser.ParserBloggerTagListPage;
import com.primecoder.webpage.spider.core.parser.ParserBloggerTagPage;
import com.primecoder.webpage.spider.core.storage.Storage;
import com.primecoder.webpage.spider.core.util.UrlType;
import com.primecoder.webpage.spider.core.util.UuidGenerate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

/**
 * Created by primecoder on 2017/8/12.
 * E-mail : aprimecoder@gmail.com
 */
public class BloggerPageDownloadByTagController {

    private static final HttpClientDownload HTTP_CLIENT_DOWNLOAD = new HttpClientDownload();

    private static final UrlDownloadedDao URL_DOWNLOADED_DAO = UrlDownloadedDao.getInstance();

    private static final ParserBloggerTagListPage PARSER_BLOGGER_TAG_LIST_PAGEPAGE = ParserBloggerTagListPage.getInstance();

    private static final ParserBloggerTagPage PARSER_BLOGGER_TAG_PAGE = ParserBloggerTagPage.getInstance();

    private static final BloggerDao BLOGGER_DAO = BloggerDao.getInstance();

    private static final String BLOGGER_BASE_PATH = "E:\\webpage\\blogger\\";

    private static final String URL_PRE = "http://www.cnblogs.com/";

    private static final Storage STORAGE = new Storage();

    private static final Logger LOGGER = LoggerFactory.getLogger(BloggerPageDownloadByTagController.class);

    public void start(String bloggerName) {


        long begin = System.currentTimeMillis();

        if (bloggerName == null) {

            return;
        }

        downloadTagList(bloggerName);

        String filePath = BLOGGER_BASE_PATH + bloggerName + "\\category-list-page.html";
        List<BloggerTagEntity> bloggerTagEntities = PARSER_BLOGGER_TAG_LIST_PAGEPAGE.parser(new File(filePath),bloggerName);

        for (BloggerTagEntity bloggerTagEntity : bloggerTagEntities) {

            int tagCount = Integer.valueOf(bloggerTagEntity.getTagCount());
            if (tagCount > 0) {
                String tagPath = downloadTag(bloggerTagEntity);

                if (tagPath == null) {
                    continue;
                }

                PARSER_BLOGGER_TAG_PAGE.parser(new File(tagPath),bloggerName,bloggerTagEntity.getTagId(),
                        bloggerTagEntity.getTagType(),bloggerTagEntity.getTagName());
            }
        }

        BLOGGER_DAO.setDownloaded(bloggerName);

        long end = System.currentTimeMillis();

        LOGGER.info("***********{}**************",end - begin);

    }

    private String getBloggerName() {

        return "";
    }

    private void downloadTagList(String bloggerName) {

        String tagListUrl = URL_PRE + bloggerName + "/mvc/blog/sidecolumn.aspx?blogApp=" + bloggerName;

        String content = HTTP_CLIENT_DOWNLOAD.download(tagListUrl);

        if (null == content) {
            return;
        }

        String filePath = BLOGGER_BASE_PATH + bloggerName + "\\category-list-page.html";

        STORAGE.storage(filePath,content);

        String path = filePath.replaceAll("\\\\","|");

        UrlDownloadedEntity urlDownloadedEntity = new UrlDownloadedEntity();
        urlDownloadedEntity.setBloggerName(bloggerName);
        urlDownloadedEntity.setType(UrlType.CATEGORYLIST.getType());
        urlDownloadedEntity.setFilepath(path);
        urlDownloadedEntity.setUrl(tagListUrl);
        URL_DOWNLOADED_DAO.insert(urlDownloadedEntity);

        LOGGER.info("download {} category list page!",bloggerName);
    }

    private String downloadTag(BloggerTagEntity bloggerTagEntity) {

        if (!bloggerTagEntity.getTagUrl().contains("cnblogs")) {

            return null;
        }

        String content = HTTP_CLIENT_DOWNLOAD.download(bloggerTagEntity.getTagUrl());

        if (null == content) {
            return null;
        }

        String tagName = bloggerTagEntity.getTagName();
        String filePath = BLOGGER_BASE_PATH + bloggerTagEntity.getBloggerName() + "\\" + tagName;
        DirMgr.mkdir(filePath);

        String tagPath = filePath + "\\tag-page.html";

        STORAGE.storage(tagPath,content);

        String path = tagPath.replaceAll("\\\\","|");

        UrlDownloadedEntity urlDownloadedEntity = new UrlDownloadedEntity();
        urlDownloadedEntity.setPageId(UuidGenerate.generate());
        urlDownloadedEntity.setBloggerName(bloggerTagEntity.getBloggerName());
        urlDownloadedEntity.setType(UrlType.CATEGORY.getType());
        urlDownloadedEntity.setFilepath(path);
        urlDownloadedEntity.setUrl(bloggerTagEntity.getTagUrl());
        URL_DOWNLOADED_DAO.insert(urlDownloadedEntity);

        return tagPath;

    }
}
