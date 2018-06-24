package stage.stages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stage.StageManager;
import stage.tasks.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @Author: duhongjiang
 * @Date: Created in 2018/5/28
 */
public class DefaultStage implements Stage {

    private static Logger logger = LoggerFactory.getLogger(DefaultStage.class);
    private StageManager stageManager;
    private Boolean started;
    private String name;
    private ExecutorService threadPool;
    //任务长度
    private AtomicInteger taskCount= new AtomicInteger(0);
    public DefaultStage(String name,ExecutorService threadPool){
        this.name=name;
        this.threadPool=threadPool;
    }
    @Override
    public void init() {

    }

    @Override
    public synchronized void start() {
        if(!this.started){
            this.setStarted(true);
        }
    }

    @Override
    public synchronized void stop() {
        this.getThreadPool().shutdown();
    }

    @Override
    public void assign(Task t) {
        if (t ==null){
            return ;
        }
        if(!this.started){
            throw new IllegalStateException("Stage"+this.name+"is inactive");
        }
        taskCount.getAndIncrement();
        if(logger.isTraceEnabled()){
            logger.trace("stage {} pending tasks: {}",name,taskCount.intValue());
        }
        t.setCurrentStage(this);
        this.getThreadPool().submit(t);


    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int pendingTasks() {
        return taskCount.intValue();
    }

    @Override
    public void taskComplete() {
        taskCount.getAndDecrement();
    }

    @Override
    public StageManager getStageManager() {
        return this.stageManager;
    }

    @Override
    public void setStageManager(StageManager stageManager) {
        this.stageManager=stageManager;
    }
    public ExecutorService getThreadPool(){ return threadPool; }
    public void setThreadPool(ExecutorService threadPool){this.threadPool= threadPool;}
    public Boolean isStarted(){return started;}
    public void setStarted(Boolean started){this.started=started;}

}
