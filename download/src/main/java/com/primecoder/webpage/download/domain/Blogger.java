package com.primecoder.webpage.download.domain;

/**
 * Created by primecoder on 2017/1/24.
 * E-mail : aprimecoder@126.com
 */
public class Blogger {

    private String blogger;

    private String bloggerUrl;

    private int pageIndexCnt;

    private int pageCnt;

    public String getBlogger() {
        return blogger;
    }

    public void setBlogger(String blogger) {
        this.blogger = blogger;
    }

    public String getBloggerUrl() {
        return bloggerUrl;
    }

    public void setBloggerUrl(String bloggerUrl) {
        this.bloggerUrl = bloggerUrl;
    }

    public int getPageIndexCnt() {
        return pageIndexCnt;
    }

    public void setPageIndexCnt(int pageIndexCnt) {
        this.pageIndexCnt = pageIndexCnt;
    }

    public int getPageCnt() {
        return pageCnt;
    }

    public void setPageCnt(int pageCnt) {
        this.pageCnt = pageCnt;
    }
}
