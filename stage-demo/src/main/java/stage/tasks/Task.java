package stage.tasks;

import stage.stages.Stage;

/**
 * @Author: duhongjiang
 * @Date: Created in 2018/5/27
 */
public interface Task extends Runnable{
    public void  setCurrentStage(Stage stage);
}
