package com.primecoder.webpage.spider.core.parser;

import com.primecoder.webpage.spider.core.dao.BloggerPageDao;
import com.primecoder.webpage.spider.core.dao.UrlDownloadedDao;
import com.primecoder.webpage.spider.core.download.HttpClientDownload;
import com.primecoder.webpage.spider.core.entity.BloggerPageEntity;
import com.primecoder.webpage.spider.core.entity.UrlDownloadedEntity;
import com.primecoder.webpage.spider.core.storage.Storage;
import com.primecoder.webpage.spider.core.util.UrlType;
import com.primecoder.webpage.spider.core.util.UuidGenerate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by primecoder on 2017/8/12.
 * E-mail : aprimecoder@gmail.com
 */
public class ParserBloggerTagPage {

    private static ParserBloggerTagPage instance = null;

    private static final BloggerPageDao BLOGGER_PAGE_DAO = BloggerPageDao.getInstance();

    private static final HttpClientDownload HTTP_CLIENT_DOWNLOAD = new HttpClientDownload();

    private static final UrlDownloadedDao URL_DOWNLOADED_DAO = UrlDownloadedDao.getInstance();

    private static final String TAG_URL = "http://www.cnblogs.com/artech/tag/WCF/default.html?page=";

    private static final String BLOGGER_BASE_PATH = "E:\\webpage\\blogger\\";

    private static final Logger LOGGER = LoggerFactory.getLogger(ParserBloggerTagPage.class);

    private static final Storage STORAGE = new Storage();

    private ParserBloggerTagPage() {

    }

    public static synchronized ParserBloggerTagPage getInstance() {

        if (null == instance) {
            instance = new ParserBloggerTagPage();
        }

        return instance;
    }

    public void parser(File file,String bloggerName,String tagId,int type,String tagName) {

        if (null == file) {
            return;
        }

        Document doc;

        try {
            doc = Jsoup.parse(file, "UTF-8", "");

            if (null != doc) {

                if (type == UrlType.CATEGORY.getType()) {

                    Elements bloggerPageClasses = doc.getElementsByClass("entrylistItem");

                    for (Element bloggerPageClass : bloggerPageClasses) {

                        String bloggerPageUrl = bloggerPageClass.childNode(1).childNode(0).attr("href");

                        if (BLOGGER_PAGE_DAO.existByUrl(bloggerPageUrl)) {
                            continue;
                        }

                        String readText = "";

                        try {

                            readText = bloggerPageClass.childNode(5).childNode(2).outerHtml();

                        } catch (IndexOutOfBoundsException e) {

                            LOGGER.error("parser category error! file : {}",file.getAbsolutePath());
                            continue;
                        }

                        int readCount = getCatagoryCount(readText);

                        if (readCount == -1) {
                            LOGGER.error("parser category error! file : {}",file.getAbsolutePath());
                            continue;
                        }

                        String commentText = bloggerPageClass.childNode(5).childNode(3).childNode(0).outerHtml();
                        int commentCount = getCatagoryCount(commentText);

                        BloggerPageEntity bloggerPageEntity = new BloggerPageEntity();
                        bloggerPageEntity.setPageId(UuidGenerate.generate());
                        bloggerPageEntity.setTagId(tagId);
                        bloggerPageEntity.setBloggerName(bloggerName);
                        bloggerPageEntity.setCommentCount(commentCount);
                        bloggerPageEntity.setReadCount(readCount);
                        bloggerPageEntity.setRecommendCount(-1);
                        bloggerPageEntity.setBloggerPageUrl(bloggerPageUrl);

                        BLOGGER_PAGE_DAO.insert(bloggerPageEntity);

                    }
                } else if (type == UrlType.TAG.getType()) {

                    Elements pageClasses = doc.getElementsByClass("Pager");

                    String text;

                    try {

                        text = pageClasses.get(0).childNode(0).childNode(0).outerHtml();

                    } catch (IndexOutOfBoundsException e) {

                        LOGGER.error("parser file : {} tag error!",file.getAbsolutePath());

                        return;
                    }

                    if (null == text) {

                        LOGGER.error("parser file : {} tag error!",file.getAbsolutePath());

                        return;
                    }

                    int pageCnt = getPageCnt(text);

                    LOGGER.info("tag type : {} page count : {}",type,pageCnt);

                    boolean flag = true;

                    flag = parserTagPage(doc,tagId,bloggerName);

                    if (!flag) {
                        LOGGER.error("parser tag page : {} has error!",file.getAbsolutePath());
                    }

                    if (pageCnt > 1) {
                        for (int i = 2;i < pageCnt + 1;i++) {

                            //http://www.cnblogs.com/artech/tag/WCF/default.html?page=
                            String tagUrl = "http://www.cnblogs.com/" + bloggerName + "/tag/"
                                    + tagName + "/default.html?page=" + i;
                            String content = HTTP_CLIENT_DOWNLOAD.download(tagUrl);

                            if (null == content) {
                                continue;
                            }

                            String filePath = BLOGGER_BASE_PATH + bloggerName + "\\" + tagName;
                            String tagPath = filePath + "\\tag-page" + i + ".html";

                            STORAGE.storage(tagPath,content);

                            String path = tagPath.replaceAll("\\\\","|");

                            UrlDownloadedEntity urlDownloadedEntity = new UrlDownloadedEntity();
                            urlDownloadedEntity.setPageId(UuidGenerate.generate());
                            urlDownloadedEntity.setBloggerName(bloggerName);
                            urlDownloadedEntity.setType(UrlType.TAG.getType());
                            urlDownloadedEntity.setFilepath(path);
                            urlDownloadedEntity.setUrl(tagUrl);
                            URL_DOWNLOADED_DAO.insert(urlDownloadedEntity);

                            Document tagDoc = Jsoup.parse(new File(tagPath), "UTF-8", "");
                            flag = parserTagPage(tagDoc,tagId,bloggerName);
                        }
                    }

                    if (!flag) {
                        LOGGER.error("parser tag page : {} has error!",file.getAbsolutePath());
                    }
                }


            }

        } catch (IOException e) {
            LOGGER.error("parser tag page : {} error : {}",file.getAbsolutePath(),e.getMessage());
        }
    }


    private boolean parserTagPage(Document doc,String tagId,String bloggerName) {

        Elements bloggerPageClasses = doc.getElementsByClass("postTitl2");

        boolean flag = true;

        for (Element bloggerPageClass : bloggerPageClasses) {
            String bloggerPageUrl = bloggerPageClass.childNode(0).attr("href");

            if (BLOGGER_PAGE_DAO.existByUrl(bloggerPageUrl)) {
                continue;
            }

            String bloggerPageCnt = null;

            try {

                bloggerPageCnt = bloggerPageClass.childNode(2).childNode(0).outerHtml();

            } catch (IndexOutOfBoundsException e) {

                LOGGER.error("parser tag page error : {}!",e.getMessage());
                flag = false;
                continue;
            }

            Map<String,Integer> cntMap = getTagCount(bloggerPageCnt);

            if (cntMap == null) {

                flag = false;
                continue;
            }

            BloggerPageEntity bloggerPageEntity = new BloggerPageEntity();
            bloggerPageEntity.setPageId(UuidGenerate.generate());
            bloggerPageEntity.setTagId(tagId);
            bloggerPageEntity.setBloggerName(bloggerName);
            bloggerPageEntity.setCommentCount(cntMap.get("commentCnt"));
            bloggerPageEntity.setReadCount(cntMap.get("readCnt"));
            bloggerPageEntity.setRecommendCount(-1);
            bloggerPageEntity.setBloggerPageUrl(bloggerPageUrl);

            BLOGGER_PAGE_DAO.insert(bloggerPageEntity);

        }

        return flag;
    }

    private int getCatagoryCount(String text) {

        int leftIndex = text.lastIndexOf("(");
        int rightIndex = text.lastIndexOf(")");

        int cnt = -1;

        try {

            cnt = Integer.valueOf(text.substring(leftIndex + 1,rightIndex));

        } catch (StringIndexOutOfBoundsException e) {

            LOGGER.error("get category count error! text is : {}",text);
        } catch (NumberFormatException e) {
            LOGGER.error("get category count error! text is : {}",text);
        }

        return cnt;
    }

    private Map<String,Integer> getTagCount(String text) {

        //Artech 2016-09-21 14:43 阅读:3637 评论:13
        int readLeft = text.indexOf("阅读:");

        try {

            String[] arr = text.substring(readLeft).split(":");
            int readCnt = Integer.valueOf(arr[1].substring(0,arr[1].indexOf(" ")));
            int commentCnt = Integer.valueOf(arr[2]);
            Map<String,Integer> cntMap = new HashMap<>();
            cntMap.put("readCnt",readCnt);
            cntMap.put("commentCnt",commentCnt);

            return cntMap;

        } catch (IndexOutOfBoundsException e) {

            LOGGER.error("get tag count error! text is : {}",text);

            return null;
        }

    }

    private int getPageCnt(String text) {

        int leftIndex = text.indexOf("共");
        int rightIndex = text.indexOf("页");
        int pageCnt = 0;

        try {

            pageCnt = Integer.valueOf(text.substring(leftIndex + 1,rightIndex));

        } catch (StringIndexOutOfBoundsException e) {

            LOGGER.error(e.getMessage());
        }

        return pageCnt;
    }
}
