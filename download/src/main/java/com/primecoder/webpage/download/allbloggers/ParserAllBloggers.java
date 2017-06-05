package com.primecoder.webpage.download.allbloggers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * Created by primecoder on 2017/1/16.
 */
public class ParserAllBloggers {


    public Map<String, String> getAllLinks(String path) {

        Map<String, String> blogers = new HashMap<String, String>();
        File input = new File(path);
        Document doc = null;
        try {
            doc = Jsoup.parse(input, "UTF-8", "");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }

        if (null != doc) {
            Elements links = doc.select("a[href]");

            for (Element link : links) {
                String url = link.absUrl("href");
                if (null == url || "".equals(url)) {
                    continue;
                }

                if (blogerUrlJudge(url)) {

                    String[] strs = url.split("/");
                    blogers.put(strs[strs.length - 1], url);

                    System.out.println("bloger:" + strs[strs.length - 1] + ",url:"
                            + url);
                }
            }
        }

        return blogers;
    }

    private boolean blogerUrlJudge(String url) {

        return (url.indexOf("http://www.cnblogs.com/") != -1)
                && (url.indexOf("rss.aspx") == -1);
    }
}
