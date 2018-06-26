package stage.stages;

//import java.util.concurrent.CompletableFuture;
import stage.DefaultStageManager;
import stage.StageManager;
import stage.coreTasks.URLDloadTask;
import stage.thread.StageThreadPoolFactory;

import java.util.concurrent.*;


/**
 * @Author: duhongjiang
 * @Date: Created in 2018/6/3
 */
public class Main {

    public static final String PARSER_STAGE = "parsing";
    public static final String DOWNLOADER_STAGE = "downloader";

//    public static final String URL_TEMPLATE = "http://api.douban.com/book/subject/";
    public static final String URL_TEMPLATE = "https://book.douban.com/subject/";

    public static final String COUNT_DOWN_LATCH = "countDownLatch";

    public static final int TASK_COUNT = 1;

    public static StageManager setUpStages() {
        StageManager sm = new DefaultStageManager();





//        sm.register(DOWNLOADER_STAGE, new FixedThreadPoolPolicy(10));
//      sm.register(DOWNLOADER_STAGE, exec);
        sm.register(DOWNLOADER_STAGE, StageThreadPoolFactory.getDownloadeStagePool());
//        sm.register(PARSER_STAGE, new SingleThreadPerCorePolicy());
        sm.register(PARSER_STAGE, StageThreadPoolFactory.getDataParsStagePool());

        sm.start();
        return sm;
    }

    public static void main(String args[]) {
        StageManager sm = setUpStages();
        CountDownLatch cdl = new CountDownLatch(TASK_COUNT);

        int idCodeBase = 25985021;
        for (int i=0; i<TASK_COUNT; i++){
            int code = idCodeBase + i;
            String url = URL_TEMPLATE + code;

            URLDloadTask task = new URLDloadTask(url);
            task.setAttribute(COUNT_DOWN_LATCH, cdl);
            sm.getStage(DOWNLOADER_STAGE).assign(task);
        }

        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sm.shutdown();

//        CompletableFuture.supplyAsync(() -> {return "sys";}
//        , new Executor() {
//            @Override
//            public void execute(Runnable runnable) {
//
//            }
//        });
    }
}
