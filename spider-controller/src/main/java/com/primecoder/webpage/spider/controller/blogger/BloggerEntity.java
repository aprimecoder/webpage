package com.primecoder.webpage.spider.controller.blogger;

/**
 * Created by primecoder on 2017/5/6.
 * E-mail : aprimecoder@gmail.com
 */
public class BloggerEntity {

    private String bloggerId;

    private String bloggerName;

    private String bloggerUrl;

    private String bloggerGuid;

    private String bloggerFollowerNum;

    private String bloggerFolloweeNum;

    public String getBloggerId() {
        return bloggerId;
    }

    public void setBloggerId(String bloggerId) {
        this.bloggerId = bloggerId;
    }

    public String getBloggerName() {
        return bloggerName;
    }

    public void setBloggerName(String bloggerName) {
        this.bloggerName = bloggerName;
    }

    public String getBloggerUrl() {
        return bloggerUrl;
    }

    public void setBloggerUrl(String bloggerUrl) {
        this.bloggerUrl = bloggerUrl;
    }

    public String getBloggerGuid() {
        return bloggerGuid;
    }

    public void setBloggerGuid(String bloggerGuid) {
        this.bloggerGuid = bloggerGuid;
    }

    public String getBloggerFollowerNum() {
        return bloggerFollowerNum;
    }

    public void setBloggerFollowerNum(String bloggerFollowerNum) {
        this.bloggerFollowerNum = bloggerFollowerNum;
    }

    public String getBloggerFolloweeNum() {
        return bloggerFolloweeNum;
    }

    public void setBloggerFolloweeNum(String bloggerFolloweeNum) {
        this.bloggerFolloweeNum = bloggerFolloweeNum;
    }
}
