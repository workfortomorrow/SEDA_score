package stage;

import stage.stages.Stage;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @Author: duhongjiang
 * @Date: Created in 2018/5/27
 */
public interface StageManager {

    public void register(Stage stage);
    public void register(String name, ExecutorService threadPool);
//    public void register(String name, ThreadPoolPolicy );
    public Stage getStage(String name);
    public List<Stage> getStages();
    public void start();
    public void shutdown();
    public ScheduledExecutorService getRetrySchduler();
    public boolean isStarted();
}
