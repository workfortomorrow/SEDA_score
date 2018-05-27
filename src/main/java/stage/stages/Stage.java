package stage.stages;

import stage.StageManager;
import stage.tasks.Task;

/**
 * @Author: duhongjiang
 * @Date: Created in 2018/5/27
 */
public interface Stage {
    public void init();
    public void start();
    public void stop();
    public void assign(Task t);
    public String getName();
    public int pendingTasks();
    public void taskComplete();
    public StageManager getStageManager();
    public void setStageManager();

}
