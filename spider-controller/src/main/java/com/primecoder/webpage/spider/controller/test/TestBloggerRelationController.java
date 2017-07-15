package com.primecoder.webpage.spider.controller.test;

import com.primecoder.webpage.spider.controller.relation.BloggerRelationController;
import com.primecoder.webpage.spider.controller.relation.QueueMgr;
import com.primecoder.webpage.spider.controller.relation.RelationTask;
import com.primecoder.webpage.spider.core.task.TaskExecute;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by primecoder on 2017/7/11.
 * E-mail : aprimecoder@gmail.com
 */
public class TestBloggerRelationController {

    private static final BloggerRelationController BLOGGER_RELATION_CONTROLLER = BloggerRelationController.getInstance();

    private static final QueueMgr QUEUE_MGR = QueueMgr.getInstance();

    private static final Logger LOGGER = LoggerFactory.getLogger(TestBloggerRelationController.class);


    public static void main(String[] args) throws InterruptedException {

        String log4jConfPath = System.getProperty("user.dir") + "/spider-core/src/main/java/log4j.properties";

        PropertyConfigurator.configure(log4jConfPath);

        TaskExecute taskExecute = TaskExecute.getInstance();

        while (true) {

            for (int i = 0;i < 5;i++) {

                RelationTask relationTask = new RelationTask();
                taskExecute.submit(relationTask);
            }

//            String bloggerId = QUEUE_MGR.get();
//
//            BLOGGER_RELATION_CONTROLLER.start(bloggerId);
        }
    }
}
