package com.primecoder.webpage.spider.core.entity;

/**
 * Created by primecoder on 2017/7/15.
 * E-mail : aprimecoder@gmail.com
 */
public class UrlDownloadedEntity {


    private String bloggerName;

    private String url;

    private String filepath;

    private int type;

    public String getBloggerName() {
        return bloggerName;
    }

    public void setBloggerName(String bloggerName) {
        this.bloggerName = bloggerName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
