package com.primecoder.webpage.spider.core.test;

import com.primecoder.webpage.spider.core.dao.DBConnection;
import com.primecoder.webpage.spider.core.task.ITask;
import com.primecoder.webpage.spider.core.task.TaskExecute;

import java.sql.Connection;

/**
 * Created by primecoder on 2017/7/15.
 * E-mail : aprimecoder@gmail.com
 */
public class TestDBConnection {

    private static final DBConnection DB_CONNECTION = DBConnection.getInstance();

    private static final TaskExecute TASK_EXECUTE = TaskExecute.getInstance();

    public static void main(String[] args) {

        while(true) {

            TASK_EXECUTE.submit(new Task(DB_CONNECTION));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

class Task implements ITask {

    private DBConnection dbConnection;

    public Task(DBConnection dbConnection) {

        this.dbConnection = dbConnection;
    }

    @Override
    public Object call() throws Exception {

        Connection connection = dbConnection.get();

        Thread.sleep(4000);

        System.out.println(Thread.currentThread().getId());

        return connection;
    }

    @Override
    public void onSuccess(Object result) {

        dbConnection.put((Connection)result);

        System.out.println(Thread.currentThread().getId());
    }

    @Override
    public void onFailure(Throwable t) {

    }
}
