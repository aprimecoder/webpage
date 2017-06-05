package com.primecoder.webpage.download.allbloggers;

import com.primecoder.webpage.download.core.Download;
import com.primecoder.webpage.download.domain.Blogger;
import com.primecoder.webpage.download.util.DBOperate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by primecoder on 2017/1/20.
 * E-mail : aprimecoder@126.com
 */
public class BloggerUtil {


    private static final String CNBLOGS_URL = "http://www.cnblogs.com/";

    private static final String bloggerUrl = "/default.html?page=0";

    private static final String bloggerUrl2 = "/default.html?page=2";

    private String baseBloggersPath = "D:/spider/cnblogs/";

    private static final BloggerUtil bloggerUtil = new BloggerUtil();

    private DBOperate dbOperate = DBOperate.getInstance();

    private Download download = new Download();

    private BloggerUtil() {

    }

    public static synchronized BloggerUtil getInstance() {

        return bloggerUtil;
    }

    public String getABloggerUrl(String blogger) {

        StringBuffer sb = new StringBuffer();
        sb.append(CNBLOGS_URL).append(blogger).append(bloggerUrl);
        return sb.toString();
    }

    public String getBloggerUrlByPage(String blogger, int page) {

        StringBuffer sb = new StringBuffer();
        sb.append(CNBLOGS_URL).append(blogger).append("/default.html?page=").append(page);
        return sb.toString();
    }

    public void updateMaxPageIndex(String blogger) {

        int max = maxPageIndex(blogger);

        dbOperate.updateMaxPageIndex(blogger, max);

    }

    private int maxPageIndex(String blogger) {

        String page2 = CNBLOGS_URL + blogger + bloggerUrl2;

        int cnt = dbOperate.queryAchievedUrl(page2);
        String path = baseBloggersPath + blogger + "/" + "2";
        if (cnt == 0) {
            boolean isDownlaod = download.download(page2, path);
            if (isDownlaod) {
                dbOperate.insertAchievedUrl(page2);
            }

        }

        Elements pageEles = parserPageClass(path, "pager");

        if (null == pageEles) {

            return 0;
        }

        for (Element page : pageEles) {
            String pageText = page.text();

            int i = pageText.indexOf("共");
            int j = pageText.indexOf("页");

            return Integer.valueOf(getRangeStr(pageText, i, j));
        }

        return 0;
    }

    private String getRangeStr(String str, int min, int max) {

        if (null == str) {
            return null;
        }

        char c1 = str.charAt(min + 1);
        char c2 = str.charAt(max - 1);
        char data[] = {c1, c2};

        return new String(data);
    }

    private void parser(){

        Elements eles = parserPageClass("D:\\spider\\cnblogs\\_franky\\0","href");
        for(Element e:eles){

            String url = e.childNode(1).attributes().get("href");
            System.out.println(url);
        }
    }

    public Elements parserPageClass(String path, String className) {
        // TODO Auto-generated method stub

        Document doc = null;

        try {
            File input = new File(path);

            doc = Jsoup.parse(input, "UTF-8", "");
        } catch (NullPointerException e) {


        } catch (FileNotFoundException e) {

            System.err.println(e.getMessage());

            parserExceptionHandle(path);
        } catch (IOException e) {
            System.err.println("not found file:" + e.getMessage());
        }

        if (null != doc) {

            return doc.getElementsByClass(className);
        }

        return null;
    }

    private void parserExceptionHandle(String path) {

        //D:\spider\cnblogs\mailingfeng\2
        String[] strs = path.split("/");

        System.out.println(strs[3]);

        String blogger = strs[3];

        if (path.endsWith("2")) {

            dbOperate.insertUnspiderUrl(getBloggerUrlByPage(blogger, 1));
            dbOperate.insertUnspiderUrl(getBloggerUrlByPage(blogger, 2));
        }
    }

    public void scanDir() {

        File file = new File(baseBloggersPath);
        File[] fileList = file.listFiles();
        for (File f : fileList) {

            String blogger;

            if (f.isDirectory()) {
                blogger = f.getName();
                String bloggerUrl = getABloggerUrl(blogger);
                //String bloggerPath = f.getAbsolutePath();

                dbOperate.insertBlogger(blogger, bloggerUrl);
            }
        }
    }

    public void refreshBlogger() {

        List<Blogger> bloggerList = dbOperate.getBlogger();
        for (Blogger bloggerEntity : bloggerList) {

            String blogger = bloggerEntity.getBlogger();
            int pageIndexCnt = bloggerEntity.getPageIndexCnt();

            for (int i = 3; i < pageIndexCnt + 1; i++) {

                String url = getBloggerUrlByPage(blogger, i);

                int cnt = dbOperate.queryAchievedUrl(url);
                String path = baseBloggersPath + blogger + "/" + String.valueOf(i);
                if (cnt == 0) {
                    boolean isDownlaod = download.download(url, path);
                    if (isDownlaod) {
                        dbOperate.insertAchievedUrl(url);
                    }else{

                        dbOperate.insertUnspiderUrl(url);
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }


            }

        }
    }

    public static void main(String[] args) {

        BloggerUtil bloggerUtil = BloggerUtil.getInstance();
        bloggerUtil.parser();
    }
}
