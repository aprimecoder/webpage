package com.primecoder.webpage.spider.controller.blogger;

import com.primecoder.core.util.uuid.UuidGenerate;
import com.primecoder.webpage.spider.core.download.HttpClientDownload;
import com.primecoder.webpage.spider.core.storage.Storage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

/**
 * Created by primecoder on 2017/5/6.
 * E-mail : aprimecoder@gmail.com
 */
public class BloggerMgr {

    private final HttpClientDownload httpClientDownload = new HttpClientDownload();

    private final Storage storage = new Storage();

    public void getBloggerEntity(String documentUrl,String bloggerUrl) {

        BloggerEntity bloggerEntity = new BloggerEntity();
        bloggerEntity.setBloggerId(UuidGenerate.generate());
        bloggerEntity.setBloggerUrl(bloggerUrl);

        String[] strs = bloggerUrl.split("/");
        String bloggerName = strs[strs.length - 1];
        bloggerEntity.setBloggerName(bloggerName);

        String documentPath = "D:\\webpage\\temp\\" + bloggerName + "-document.html";

        String content = httpClientDownload.download(documentUrl);
        storage.storage(documentPath,content);

        Document doc;

        File file = new File(documentPath);
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
                                bloggerEntity.setBloggerGuid(s.substring(1,s.length() - 1));
                                break;
                            }
                        }
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
