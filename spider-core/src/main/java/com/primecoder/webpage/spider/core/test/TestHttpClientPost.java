package com.primecoder.webpage.spider.core.test;

import com.primecoder.webpage.spider.core.download.HttpClientPost;

/**
 * Created by primecoder on 2017/7/8.
 * E-mail : aprimecoder@gmail.com
 */
public class TestHttpClientPost {

    public static void main(String[] args) {

        HttpClientPost httpClientPost = HttpClientPost.getInstance();
        httpClientPost.postBloggerRelation(null);
    }
}
