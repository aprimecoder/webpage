package com.primecoder.webpage.download.allbloggers;

import com.primecoder.webpage.download.core.Download;
import com.primecoder.webpage.download.core.GenerateId;
import com.primecoder.webpage.download.util.DBOperate;
import com.primecoder.webpage.download.util.DirUtil;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by primecoder on 2017/1/16.
 */
public class DownloadAllBloggers {

    private Download download = new Download();

    private DirUtil dirUtil = new DirUtil();

    private String rankingAllBloggersUrl = "http://www.cnblogs.com/AllBloggers.aspx";

    private String rankingAllBloggerPath = "D:/spider/cnblogs/AllBlogger.html";

    private String baseBloggersPath = "D:/spider/cnblogs/";

    private ParserAllBloggers parserAllBloggers = new ParserAllBloggers();

    private DBOperate dbOperate = DBOperate.getInstance();

    private BloggerUtil bloggerUtil = BloggerUtil.getInstance();

    public void download(){

        int cnt = dbOperate.queryAchievedUrl(rankingAllBloggersUrl);
        if (cnt == 0) {
            boolean isDownload = download.download(rankingAllBloggersUrl,rankingAllBloggerPath);
            if (isDownload){
                dbOperate.insertAchievedUrl(rankingAllBloggersUrl);
            }

        }

        Map<String,String> links = parserAllBloggers.getAllLinks(rankingAllBloggerPath);

        Set<String> keys = links.keySet();
        Iterator<String> keyIter = keys.iterator();
        while (keyIter.hasNext()) {
            String blogger = keyIter.next();
            String bloggerUrl = bloggerUtil.getABloggerUrl(blogger);
            String path = mkdirForBlogger(blogger);
            System.out.println(blogger + " url:" + bloggerUrl);

            int tempCnt = dbOperate.queryAchievedUrl(bloggerUrl);
            if (tempCnt != 0) {
                continue;
            }
            boolean isDownload = download.download(bloggerUrl,path + 0);
            if(isDownload){
                System.out.println("downloaded blogger:" + blogger);
                dbOperate.insertAchievedUrl(bloggerUrl);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private String mkdirForBlogger(String blogger){

        String path = baseBloggersPath + blogger + "/";
        dirUtil.mkdir(path);
        return path;
    }
}
