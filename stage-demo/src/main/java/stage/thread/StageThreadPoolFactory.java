package stage.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: duhongjiang
 * @Date: Created in 2018/6/24
 */
public  class StageThreadPoolFactory {


    /**
     * 以下参数由spring容器启动时，从数据库中加载到内存中(参数常量类)
     * 先暂时写死
     */

    //核心线程池大小
    static int corePoolSize=2;
    //最大线程池大小
    static int maximumPoolSize = 3;
    //空闲线程最大存活时间
    static long keepAliveTime =0L;
    //keepAliveTime时间单位
    static TimeUnit timeUnit = TimeUnit.MICROSECONDS;
    //阻塞  工作队列
    static BlockingQueue<Runnable> workQueue= new LinkedBlockingDeque<>(5);

    public static ThreadPoolExecutor exec;

    public static ThreadPoolExecutor downloadeStagePool;

    public static ThreadPoolExecutor dataParsStagePool;



    public static void init (){

        initDataParsStagePool();
        initDownloadeStagePool();
    }


    public static void initDownloadeStagePool(){
        //从参数常量类中获取 对应 参数

        dataParsStagePool= new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime, timeUnit,workQueue);
    }

    public static void initDataParsStagePool(){
        //从参数常量类中获取 对应 参数

        dataParsStagePool=new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime, timeUnit,workQueue);
    }


    public static  ThreadPoolExecutor getThreadPoolExcecutot(){

        return new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime, timeUnit,workQueue);
    }

    public static ThreadPoolExecutor getDownloadeStagePool(){
        //获取 下载stage对应的线程池
//        downloadeStagePool.setCorePoolSize(1);
//        downloadeStagePool.setMaximumPoolSize(1);
        /**
         *  队列不能用 线程池里的工作队列   原因是其不能在运行时修改  不能动态更新
         */

        return downloadeStagePool;
    }
    public static ThreadPoolExecutor getDataParsStagePool(){
        return dataParsStagePool;
    }

    public  void setDownloadeStagePool(ThreadPoolExecutor downloadeStagePool) {
        StageThreadPoolFactory.downloadeStagePool = downloadeStagePool;
    }

    public  void setDataParsStagePool(ThreadPoolExecutor dataParsStagePool) {
        StageThreadPoolFactory.dataParsStagePool = dataParsStagePool;
    }















}
