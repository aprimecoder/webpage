package com.primecoder.webpage.download.allbloggers;

import com.primecoder.webpage.download.TaskMgr;
import com.primecoder.webpage.download.core.Download;
import com.primecoder.webpage.download.util.DBOperate;
import com.primecoder.webpage.download.util.DirUtil;

import java.util.List;

/**
 * Created by primecoder on 2017/1/27.
 * E-mail : aprimecoder@126.com
 */
public class GetAllPage {


    private final DBOperate dbOperate = DBOperate.getInstance();

    private static final TaskMgr taskMgr = new TaskMgr();

    public void execute() {


        List<String> allBloggers = dbOperate.getAllBloggers();

        for (String blogger : allBloggers) {

            System.out.println("******************************begin to download blogger:" + blogger);

            List<String> urlList = dbOperate.getUrl(blogger);

            int max = 0;

            for (String url : urlList) {

                Task task = new Task(blogger,max,url);

                taskMgr.addTask(task);

                max = max + 1;

                if (max/20 == 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

            System.out.println("*********************************end download blogger:" + blogger);
            try {
                Thread.sleep(5*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        GetAllPage getAllPage = new GetAllPage();
        getAllPage.execute();
    }
}

class Task implements Runnable{

    private static String baseBloggersPath = "D:/spider/cnblogs/";

    private static DirUtil dirUtil = new DirUtil();

    private static Download download = new Download();

    private static final DBOperate dbOperate = DBOperate.getInstance();

    private int max;

    private String blogger;

    private String url;

    public Task(String blogger,int max,String url){

        this.blogger = blogger;

        this.max = max;

        this.url = url;
    }

    public void run() {

        int cnt = dbOperate.queryAchievedUrl(url);

        if (cnt == 0) {

            String path = baseBloggersPath + blogger + "/page";

            dirUtil.mkdir(path);

            String pagePath = path + "/" + max;

            boolean isDownload = download.download(url, pagePath);
            if (isDownload) {
                dbOperate.insertAchievedUrl(url);
                max = max + 1;

                System.out.println("downloaded blogger:" + blogger + ",url:" + url);

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
