package com.primecoder.webpage.spider.controller.test;

/**
 * Created by primecoder on 2017/7/8.
 * E-mail : aprimecoder@gmail.com
 */
public class TestPageMax {

    public static void main(String[] args) {

        String pageInfo = "\"<div class=\\\"pager\\\"><span class=\\\"current\\\">1</span><a href=\\\"#\\\" onclick=\\\"getRelationUsers('00000000-0000-0000-0000-000000000000',2,45);return false;\\\">2</a><a href=\\\"#\\\" onclick=\\\"getRelationUsers('00000000-0000-0000-0000-000000000000',3,45);return false;\\\">3</a><a href=\\\"#\\\" onclick=\\\"getRelationUsers('00000000-0000-0000-0000-000000000000',4,45);return false;\\\">4</a><a href=\\\"#\\\" onclick=\\\"getRelationUsers('00000000-0000-0000-0000-000000000000',5,45);return false;\\\">5</a><a href=\\\"#\\\" onclick=\\\"getRelationUsers('00000000-0000-0000-0000-000000000000',2,45);return false;\\\">Next &gt;</a></div>\"";

        int hrefIndex = pageInfo.lastIndexOf("href");
        String temp = pageInfo.substring(0,hrefIndex -7);
        int largeIndex = temp.lastIndexOf(">");
        String result = pageInfo.substring(largeIndex + 1,hrefIndex -7);

        System.out.println(result);
    }
}
