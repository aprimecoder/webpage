package com.primecoder.webpage.download.allbloggers;

import com.primecoder.webpage.download.util.DBOperate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by primecoder on 2017/1/26.
 * E-mail : aprimecoder@126.com
 */
public class GetAllUrls {


    private final BloggerUtil bloggerUtil = BloggerUtil.getInstance();

    private final DBOperate dbOperate = DBOperate.getInstance();

    private static final String CNBLOGS_URL = "http://www.cnblogs.com/";

    private static final String COMMON = "/p/";


    public static void main(String[] args){

        GetAllUrls getAllUrls = new GetAllUrls();
        getAllUrls.scan();
    }

    public void scan() {

        String path = "D:\\spider\\cnblogs\\";

        File file = new File(path);

        File[] files = file.listFiles();

        for (File f : files) {

            if (f.isDirectory()) {

                Set<String> urlSet = new HashSet<String>();
                String blogger = f.getName();
                String pre = CNBLOGS_URL + blogger;

                File[] pageFiles = f.listFiles();
                for (File page : pageFiles) {
                    Elements eles = getHerfs(page.getAbsolutePath());
                    for (Element e : eles) {

                        String url = e.attributes().get("href");

                        if ((!url.equals(pre))&&(url.startsWith(pre))){
                            System.out.println(url);
                            urlSet.add(url);
                        }
                    }
                }

                storage(urlSet,blogger);

            }
        }
    }

    private Elements getHerfs(String path){

        Document doc = null;

        try {
            File input = new File(path);

            doc = Jsoup.parse(input, "UTF-8", "");
        } catch (NullPointerException e) {

        } catch (FileNotFoundException e) {

            System.err.println(e.getMessage());

        } catch (IOException e) {
            System.err.println("not found file:" + e.getMessage());
        }

        if (null != doc) {

            return doc.select("a[href]");

        }

        return null;
    }

    private void storage(Set<String> urlSet,String blogger) {

        if (urlSet == null) {
            return;
        }

        Iterator<String> iter = urlSet.iterator();
        while(iter.hasNext()){

            String url = iter.next();
            dbOperate.insertUrl(url,blogger);
        }
    }

}
