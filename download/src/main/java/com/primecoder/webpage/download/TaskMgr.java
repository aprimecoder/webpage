package com.primecoder.webpage.download;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by primecoder on 2017/3/4.
 * E-mail : aprimecoder@126.com
 */
public class TaskMgr {

    private static final int NTHREADS = 50;

    private static final Executor exec
            = Executors.newFixedThreadPool(NTHREADS);

    public void addTask(Runnable runnable) {

        exec.execute(runnable);
    }
}
