package com.primecoder.webpage.spider.controller.relation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by primecoder on 2017/7/13.
 * E-mail : aprimecoder@gmail.com
 */
public class RelationRejectedExecutionHandler implements RejectedExecutionHandler {


    private static final Logger LOGGER = LoggerFactory.getLogger(RelationRejectedExecutionHandler.class);

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

        LOGGER.info("###############reject task,current queue size :{}",String.valueOf(executor.getQueue().size()));
    }
}
