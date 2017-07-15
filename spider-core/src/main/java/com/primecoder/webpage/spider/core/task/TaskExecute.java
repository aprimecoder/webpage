package com.primecoder.webpage.spider.core.task;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * Created by primecoder on 2017/7/12.
 * E-mail : aprimecoder@gmail.com
 */
public class TaskExecute {


    private static TaskExecute instance = null;

    private static ListeningExecutorService listeningExecutorService = null;

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskExecute.class);

    private static final BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(1000);

    private TaskExecute() {

        ExecutorService executorServive = new ThreadPoolExecutor(10, 20, 10,
                TimeUnit.MILLISECONDS, workQueue,new RelationRejectedExecutionHandler());

        listeningExecutorService = MoreExecutors.listeningDecorator(executorServive);
    }

    public static synchronized TaskExecute getInstance() {

        if (null == instance) {
            instance = new TaskExecute();
        }

        return instance;
    }

    public void submit(ITask task) {

        ListenableFuture listenableFuture = listeningExecutorService.submit(task);

        Futures.addCallback(listenableFuture, task);

    }
}
