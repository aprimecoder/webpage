package com.primecoder.webpage.spider.core.test;

import com.primecoder.webpage.spider.core.download.HttpClientDownload;
import com.primecoder.webpage.spider.core.storage.Storage;

/**
 * Created by primecoder on 2017/7/8.
 * E-mail : aprimecoder@gmail.com
 */
public class TestHttpClientDownload {

    public static void main(String[] args) {


        HttpClientDownload httpClientDownload = new HttpClientDownload();
        String content = httpClientDownload.download("http://www.cnblogs.com/fengmk2/");

        Storage storage = new Storage();
        storage.storage("E:\\webpage\\temp\\test.html",content);
    }
}
