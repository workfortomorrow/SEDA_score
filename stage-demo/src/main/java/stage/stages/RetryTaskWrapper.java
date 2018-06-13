package stage.stages;

import stage.tasks.AbstractRetryableTask;

/**
 * @Author: duhongjiang
 * @Date: Created in 2018/6/3
 */
public class RetryTaskWrapper implements Runnable {

    private  AbstractRetryableTask task;

    public  RetryTaskWrapper(AbstractRetryableTask task){
        this.task=task;
    }
    @Override
    public void run() {
        task.getCurrentStage().assign(task);
    }
}
