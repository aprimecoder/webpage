package com.primecoder.webpage.spider.core.util;

/**
 * Created by primecoder on 2017/8/12.
 * E-mail : aprimecoder@gmail.com
 */
public enum UrlType {

    CATEGORY(0),HOMEPAGE(1),BLOGGERPAGE(2),CATEGORYLIST(3),TAG(4),TAGLIST(5);

    private int type;

    public int getType() {

        return this.type;
    }

    UrlType(int type) {

        this.type = type;
    }
}
