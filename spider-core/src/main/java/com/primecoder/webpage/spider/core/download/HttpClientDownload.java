package com.primecoder.webpage.spider.core.download;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UTFDataFormatException;

/**
 * Created by primecoder on 2017/5/6.
 * E-mail : aprimecoder@gmail.com
 */
public class HttpClientDownload {


    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientDownload.class);

    public String download(String url) {

        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse;
        try {
            httpResponse = httpClient.execute(httpGet);

            LOGGER.info("download url:{} success",url);

            return EntityUtils.toString(httpResponse.getEntity());

        } catch(UTFDataFormatException e){

            LOGGER.error("download url:{} error,msg:{}",url,e.getMessage());

        } catch(IOException e) {

            LOGGER.error("download url:{} error,msg:{}",url,e.getMessage());

        }

        return null;

    }
}
