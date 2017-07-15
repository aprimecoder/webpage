package com.primecoder.webpage.spider.controller.relation;

import com.primecoder.webpage.spider.core.task.ITask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by primecoder on 2017/7/13.
 * E-mail : aprimecoder@gmail.com
 */
public class RelationTask implements ITask{


    private static final BloggerRelationController BLOGGER_RELATION_CONTROLLER = BloggerRelationController.getInstance();

    private static final QueueMgr QUEUE_MGR = QueueMgr.getInstance();

    private static final Logger LOGGER = LoggerFactory.getLogger(RelationTask.class);

    @Override
    public Object call() throws Exception {

        String bloggerId = QUEUE_MGR.get();

        return BLOGGER_RELATION_CONTROLLER.start(bloggerId);
    }

    @Override
    public void onSuccess(Object result) {

        LOGGER.info("*********success*************" + result);

    }

    @Override
    public void onFailure(Throwable t) {

        LOGGER.info("*********failed*************" + t.getMessage());
    }
}
