package com.primecoder.webpage.spider.controller.homepage;

import com.primecoder.core.util.date.CurrentDate;
import com.primecoder.core.util.dir.DirMgr;
import com.primecoder.webpage.spider.core.download.HttpClientDownload;
import com.primecoder.webpage.spider.core.storage.Storage;

/**
 * Created by primecoder on 2017/5/6.
 * E-mail : aprimecoder@gmail.com
 */
public class HomePageDownloadController {

    private final HttpClientDownload httpClientDownload = new HttpClientDownload();

    private final Storage storage = new Storage();

    public void start() {

        String dayBasePath = HomePageStoragePath.HOME_PAGE_STORAGE_BASE_PATH + CurrentDate.getDateString();

        DirMgr.mkdir(dayBasePath);

        for (int i=1;i<HomePageUrlMgr.HOME_PAGE_MAX_INDEX + 1;i++) {

            String pagePath = dayBasePath + "\\" + i + ".html";
            String url = HomePageUrlMgr.HOME_PAGE_BASE_URL + i;

            String content = httpClientDownload.download(url);
            if (null == content) {
                //save to database
                continue;
            } else {
                storage.storage(pagePath,content);
            }
        }
    }
}
