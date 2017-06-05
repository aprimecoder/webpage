package com.primecoder.webpage.download;

import com.primecoder.webpage.download.allbloggers.BloggerUtil;
import com.primecoder.webpage.download.allbloggers.DownloadAllBloggers;
import com.primecoder.webpage.download.allbloggers.DownloadBlogger;
import com.primecoder.webpage.download.allbloggers.ParserAllBloggers;
import com.primecoder.webpage.download.core.Download;
import com.primecoder.webpage.download.core.GenerateId;

/**
 * Created by primecoder on 2017/1/16.
 */
public class DownloadController {

    private DownloadAllBloggers downloadAllBloggers = new DownloadAllBloggers();

    private BloggerUtil bloggerUtil = BloggerUtil.getInstance();

    public static void main(String[] args){

        DownloadController downloadController = new DownloadController();
        //downloadController.downloadAllBloggers();
        //downloadController.scanDir();
        //downloadController.maxPageIndex();
        //downloadController.updateMaxPageIndex();
        //downloadController.parserAllBloggers();
        //downloadController.downloadBlogger();
        downloadController.refresh();
    }

    private void downloadAllBloggers(){

        downloadAllBloggers.download();
    }

    private void scanDir(){

        bloggerUtil.scanDir();
    }

//    private void maxPageIndex(){
//
//       int max = bloggerUtil.maxPageIndex("81");
//       System.out.println(max);
//    }

    private void updateMaxPageIndex(){

        bloggerUtil.updateMaxPageIndex("81");
    }

    private void parserAllBloggers(){
        ParserAllBloggers parserAllBloggers = new ParserAllBloggers();
        parserAllBloggers.getAllLinks("D:/spider/cnblogs/AllBlogger.html");
    }

    private void downloadBlogger(){

        DownloadBlogger downloadBlogger = new DownloadBlogger();
        downloadBlogger.download();
    }

    public void refresh(){

        bloggerUtil.refreshBlogger();
    }

}
