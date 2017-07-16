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

        if (url == null) {
            return null;
        }

        HttpGet httpGet = new HttpGet(url);

        httpGet.setHeader("Cookie","__gads=ID=8b7984e53b82151f:T=1484577342:S=ALNI_MbIReNd92JuLvC-A54Zdhs3M39y5g; UM_distinctid=15b2e6e9b4b3fd-07c99bf36915e4-5b123112-1fa400-15b2e6e9b4c3c7; pgv_pvi=7930592256; __utma=226521935.1600030032.1488602042.1499781361.1499899554.2; __utmz=226521935.1499781361.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); CONTAINERID=1c1901578b3d862a78d2fb7f9df73f2d6ae1786b2dc9af1c756743e95753ca1e; .CNBlogsCookie=14B0A0DDFA7F1D027811758FD293F4871C237FDB96703F74BBBB8E88A341429C3F93DB21FE4ADD2503D2E791ECB3DAF30546927184BBA2F13FADCB44932100195761B5C8FE344D35CF327BF87D9165C24490F277; .Cnblogs.AspNetCore.Cookies=CfDJ8PhlBN8IFxtHhqIV3s0LCDk6YhT17yhuc20CPCROrzNds233bVuDdazTiyws0hyXFJqZZLEj5FvOj2YThWQJXGo2IBKNEZJKsCY8hEngYYudK6JwG4LfMRnpwAQOpxL5RF0JECsftIi_UhrAht3nBbcw61qpSCF031yd68G52BI-XtPR4PoUETmxNHveopEKNRqe7BoVDZk9lFmYspGWi4cFz3b0K4xb1qznWAYehPNm-HUHvF75pWTdvj_B4qhH_W9Z8BwHrOOTnQBpi22OrH8T289jlY8QhDPO695CZ4Q-; _ga=GA1.2.1600030032.1488602042; _gid=GA1.2.1875944549.1500096857; _gat=1");

        HttpResponse httpResponse;
        try {
            httpResponse = httpClient.execute(httpGet);

            LOGGER.info("download url:{} success",url);

            return EntityUtils.toString(httpResponse.getEntity());

        } catch(UTFDataFormatException e){

            LOGGER.error("download url:{} error,msg:{}",url,e.getMessage());

        } catch(IOException e) {

            LOGGER.error("download url:{} error,msg:{}",url,e.getMessage());

        } catch (IllegalStateException e) {

            LOGGER.error("download url:{} error,msg:{}",url,e.getMessage());
        }

        return null;

    }
}
