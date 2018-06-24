package stage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stage.stages.DefaultStage;
import stage.stages.Stage;
import stage.thread.NamedThreadFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @Author: duhongjiang
 * @Date: Created in 2018/6/24
 */
public class DefaultStageManager implements StageManager {

    //newScheduledThreadPool 定长线程池，可定时周期性任务执行
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1,new NamedThreadFactory("retry pool"));
    private Map<String ,Stage> stages = new ConcurrentHashMap<String, Stage>();
    private static Logger logger = LoggerFactory.getLogger(DefaultStage.class);
    private boolean started;
    @Override
    public void register(Stage stage) {
        stages.put(stage.getName(),stage);
        stage.setStageManager(this);
        stage.init();
    }

    @Override
    public void register(String name, ExecutorService threadPool) {
        Stage stage = new DefaultStage(name, threadPool);
        this.register(stage);
    }

    @Override
    public Stage getStage(String name) {

        if(name != null){
            if (stages.containsKey(name)){
                return stages.get(name);
            }else {
                logger.error("no such stage: "+ name);
            }

        }else {
            logger.error("stage name must not null "+name);
        }
        return null;
    }

    @Override
    public List<Stage> getStages() {
        return new ArrayList<Stage>(stages.values());
    }

    @Override
    public void start() {
      for(Stage stage : stages.values()){
          stage.start();
      }
        started = true;
    }

    @Override
    public void shutdown() {
        for (Stage stage : stages.values()){
            stage.stop();
        }
        started = false;
    }

    @Override
    public ScheduledExecutorService getRetrySchduler() {
        return scheduledExecutorService;
    }

    @Override
    public boolean isStarted() {
        return started;
    }
}
