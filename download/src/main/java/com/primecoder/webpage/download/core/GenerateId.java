package com.primecoder.webpage.download.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by primecoder on 2017/1/16.
 */
public class GenerateId {


    private final Map<String,Integer> maxIdMap = new HashMap<String,Integer>();

    public int getBloggerNextId(String blogger){

        boolean keyFlag = maxIdMap.containsKey(blogger);

        int bloggerMaxId = 0;

        if(keyFlag){

            bloggerMaxId = maxIdMap.get(blogger);
            bloggerMaxId++;

        }else{
            bloggerMaxId++;

        }

        maxIdMap.put(blogger,bloggerMaxId);

        return bloggerMaxId;
    }

    public static void main(String[] args){

        GenerateId generateId = new GenerateId();
        generateId.getBloggerNextId("zhao");
        generateId.getBloggerNextId("zhong");
        generateId.getBloggerNextId("zhao");
    }
}
