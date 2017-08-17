package com.primecoder.webpage.spider.core.parser;

import com.primecoder.webpage.spider.core.dao.BloggerTagDao;
import com.primecoder.webpage.spider.core.dao.UrlDownloadedDao;
import com.primecoder.webpage.spider.core.download.HttpClientDownload;
import com.primecoder.webpage.spider.core.entity.BloggerTagEntity;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by primecoder on 2017/8/12.
 * E-mail : aprimecoder@gmail.com
 */
public class ParserBloggerTagListPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParserBloggerTagListPage.class);

    private static ParserBloggerTagListPage parserBloggerTagPage = null;

    private static final BloggerTagDao BLOGGER_TAG_DAO = BloggerTagDao.getInstance();

    private static final HttpClientDownload HTTP_CLIENT_DOWNLOAD = new HttpClientDownload();

    private static final String URL_PRE = "http://www.cnblogs.com/";

    private static final String BLOGGER_BASE_PATH = "E:\\webpage\\blogger\\";

    private static final UrlDownloadedDao URL_DOWNLOADED_DAO = UrlDownloadedDao.getInstance();

    private static final Storage STORAGE = new Storage();

    private ParserBloggerTagListPage() {

    }

    public static synchronized ParserBloggerTagListPage getInstance() {

        if (null == parserBloggerTagPage) {

            parserBloggerTagPage = new ParserBloggerTagListPage();
        }

        return parserBloggerTagPage;
    }

    public List<BloggerTagEntity> parser(File file,String bloggerName) {

        if (null == file) {
            return null;
        }

        Document doc;

        List<BloggerTagEntity> tagList = new ArrayList<>();

        try {
            doc = Jsoup.parse(file, "UTF-8", "");
            Elements es = doc.getElementsByTag("li");

            for (Element e : es) {

                String url = e.childNode(0).attr("href");

                LOGGER.info("bloggerName : {} tag url : {}",bloggerName,url);

                if ((url != null) && url.contains("category")) {

                    String text = e.childNode(0).childNode(0).outerHtml();

                    Map<String,String> tagMap = getTagName(text);

                    BloggerTagEntity bloggerTagEntity = new BloggerTagEntity();

                    bloggerTagEntity.setTagId(UuidGenerate.generate());

                    bloggerTagEntity.setBloggerName(bloggerName);
                    bloggerTagEntity.setTagCount(tagMap.get("count"));
                    bloggerTagEntity.setTagName(tagMap.get("name"));

                    bloggerTagEntity.setTagUrl(url);

                    bloggerTagEntity.setTagType(UrlType.CATEGORY.getType());

                    BLOGGER_TAG_DAO.insert(bloggerTagEntity);

                    tagList.add(bloggerTagEntity);
                }
            }
        } catch (IOException e) {

            LOGGER.info("parser file : {} tag error : {}!",file.getAbsolutePath(),e.getMessage());
        }

        achieveTagList(bloggerName,tagList);

        return tagList;

    }

    private Map<String,String> getTagName(String text) {

        //01 Java 基础语法(12)
        int rightIndex = text.lastIndexOf("(");
        int rightIndex0 = text.lastIndexOf(")");

        if (rightIndex < 0) {
            Map<String,String> tagMap = new HashMap<>();
            tagMap.put("name",text);
            tagMap.put("count","0");
            return tagMap;
        }

        LOGGER.info(text);

        String name = text.substring(0,rightIndex);
        String count = text.substring(rightIndex + 1,rightIndex0);

        try {
            Integer.valueOf(count);
        } catch (NumberFormatException e) {
            Map<String,String> tagMap = new HashMap<>();
            tagMap.put("name",text);
            tagMap.put("count","0");
            return tagMap;
        }


        Map<String,String> tagMap = new HashMap<>();
        tagMap.put("name",name);
        tagMap.put("count",count);

        return tagMap;
    }

    private void achieveTagList(String bloggerName,List<BloggerTagEntity> tagList) {

        String tagListUrl = URL_PRE + bloggerName + "/tag/";

        String content = HTTP_CLIENT_DOWNLOAD.download(tagListUrl);

        if (null == content) {
            return;
        }

        String tagPath = BLOGGER_BASE_PATH + bloggerName + "\\tag-list-page.html";

        STORAGE.storage(tagPath,content);

        String path = tagPath.replaceAll("\\\\","|");

        UrlDownloadedEntity urlDownloadedEntity = new UrlDownloadedEntity();
        urlDownloadedEntity.setBloggerName(bloggerName);
        urlDownloadedEntity.setType(UrlType.TAGLIST.getType());
        urlDownloadedEntity.setFilepath(path);
        urlDownloadedEntity.setUrl(tagListUrl);
        URL_DOWNLOADED_DAO.insert(urlDownloadedEntity);

        LOGGER.info("download {} tag list page!",bloggerName);

        Document doc;

        try {
            doc = Jsoup.parse(new File(tagPath), "UTF-8", "");

            Elements links = doc.getElementsByTag("td");

            for (Element link : links) {

                if (link.childNodes().size() > 1) {

                    String tagText = "";
                    String tagUrl = "";
                    String countText = "";
                    String tagCount = "";
                    try {
                        tagText = link.childNode(1).childNode(0).outerHtml();
                        tagUrl = link.childNode(1).attr("href");
                        countText = link.childNode(2).childNode(0).outerHtml();
                        tagCount = countText.substring(1,countText.length() - 1);
                    } catch (IndexOutOfBoundsException e) {

                        continue;
                    }

                    BloggerTagEntity bloggerTagEntity = new BloggerTagEntity();
                    bloggerTagEntity.setTagId(UuidGenerate.generate());
                    bloggerTagEntity.setTagName(tagText);
                    bloggerTagEntity.setTagUrl(tagUrl);
                    bloggerTagEntity.setBloggerName(bloggerName);
                    bloggerTagEntity.setTagCount(tagCount);
                    bloggerTagEntity.setTagType(UrlType.TAG.getType());

                    BLOGGER_TAG_DAO.insert(bloggerTagEntity);

                    tagList.add(bloggerTagEntity);
                }

            }

        } catch (IOException e) {

            LOGGER.error("parser tag list page : {} error : {}",tagPath,e.getMessage());
        }

        LOGGER.info("parser tag list page : {} success!",tagPath);
    }
}
