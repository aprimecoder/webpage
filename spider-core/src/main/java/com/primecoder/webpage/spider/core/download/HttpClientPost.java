package com.primecoder.webpage.spider.core.download;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.primecoder.webpage.spider.core.util.CookieMgr;
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

    private static final CookieMgr COOKIE_MGR = CookieMgr.getInstance();

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
        httpPost.setHeader("Cookie",COOKIE_MGR.getCookie());
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
