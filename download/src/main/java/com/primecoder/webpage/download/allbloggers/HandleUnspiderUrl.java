package com.primecoder.webpage.download.allbloggers;

import com.primecoder.webpage.download.util.DBOperate;

import java.util.List;

/**
 * Created by primecoder on 2017/1/24.
 * E-mail : aprimecoder@126.com
 */
public class HandleUnspiderUrl {

    private DBOperate dbOperate = DBOperate.getInstance();

    public void handle(){

        List<String> unspiderUrlList = dbOperate.getUnspiderUrlList();
        if (null != unspiderUrlList){

            for(String url : unspiderUrlList){


            }

        }
    }
}
