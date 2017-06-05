package com.primecoder.webpage.download.allbloggers;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by primecoder on 2017/1/20.
 * E-mail : aprimecoder@126.com
 */
public class DownloadBlogger {

    private BloggerUtil bloggerUtil = BloggerUtil.getInstance();

    public void download(){

        ParserAllBloggers parserAllBloggers = new ParserAllBloggers();
        Map<String,String> allBloggerslinks = parserAllBloggers.getAllLinks("D:/spider/cnblogs/AllBlogger.html");
        Set<String> keys = allBloggerslinks.keySet();
        Iterator<String> keyIter = keys.iterator();
        while (keyIter.hasNext()) {

            String blogger = keyIter.next();

            bloggerUtil.updateMaxPageIndex(blogger);

            try {
                Thread.sleep(1*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
