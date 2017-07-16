package com.primecoder.webpage.spider.core.download;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by primecoder on 2017/7/8.
 * E-mail : aprimecoder@gmail.com
 */
public class HttpClientPost {


    private static final String URL = "https://home.cnblogs.com/relation_users";

    public static final Logger LOGGER = LoggerFactory.getLogger(HttpClient.class);

    private static HttpClientPost instance = null;

    private HttpClientPost() {

    }

    public static synchronized HttpClientPost getInstance() {

        if (null == instance) {
            instance = new HttpClientPost();
        }

        return instance;
    }

    public JSONObject postBloggerRelation(JSONObject obj) {

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(URL);

        httpPost.setHeader("Accept","application/json");
        httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        httpPost.setHeader("Cookie","__gads=ID=8b7984e53b82151f:T=1484577342:S=ALNI_MbIReNd92JuLvC-A54Zdhs3M39y5g; UM_distinctid=15b2e6e9b4b3fd-07c99bf36915e4-5b123112-1fa400-15b2e6e9b4c3c7; pgv_pvi=7930592256; __utma=226521935.1600030032.1488602042.1499781361.1499899554.2; __utmz=226521935.1499781361.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); CONTAINERID=1c1901578b3d862a78d2fb7f9df73f2d6ae1786b2dc9af1c756743e95753ca1e; .CNBlogsCookie=14B0A0DDFA7F1D027811758FD293F4871C237FDB96703F74BBBB8E88A341429C3F93DB21FE4ADD2503D2E791ECB3DAF30546927184BBA2F13FADCB44932100195761B5C8FE344D35CF327BF87D9165C24490F277; .Cnblogs.AspNetCore.Cookies=CfDJ8PhlBN8IFxtHhqIV3s0LCDk6YhT17yhuc20CPCROrzNds233bVuDdazTiyws0hyXFJqZZLEj5FvOj2YThWQJXGo2IBKNEZJKsCY8hEngYYudK6JwG4LfMRnpwAQOpxL5RF0JECsftIi_UhrAht3nBbcw61qpSCF031yd68G52BI-XtPR4PoUETmxNHveopEKNRqe7BoVDZk9lFmYspGWi4cFz3b0K4xb1qznWAYehPNm-HUHvF75pWTdvj_B4qhH_W9Z8BwHrOOTnQBpi22OrH8T289jlY8QhDPO695CZ4Q-; _ga=GA1.2.1600030032.1488602042; _gid=GA1.2.1875944549.1500096857; _gat=1");
        httpPost.setHeader("Content-Type","application/json; charset=UTF-8");

        try {
            httpPost.setEntity(new StringEntity(obj.toJSONString()));
            HttpResponse response = httpClient.execute(httpPost);

            if (response.getStatusLine().getStatusCode() == 200) {

                String result = EntityUtils.toString(response.getEntity());

                JSONObject resultObj = JSON.parseObject(result);

                return resultObj;

//                JSONArray userArr = resultObj.getJSONArray("Users");
//                for (int i=0;i<userArr.size();i++) {
//                    JSONObject user = userArr.getJSONObject(i);
//                    String bloggerName = user.getString("Alias");
//                    System.out.println(bloggerName);
//                }

            }
        } catch (UnsupportedEncodingException e) {

            LOGGER.error("post obj : {} error : {}",obj.toJSONString(),e.getMessage());

        } catch (ClientProtocolException e) {

            LOGGER.error("post obj : {} error : {}",obj.toJSONString(),e.getMessage());
        } catch (IOException e) {

            LOGGER.error("post obj : {} error : {}",obj.toJSONString(),e.getMessage());
        }

        return null;
    }
}
