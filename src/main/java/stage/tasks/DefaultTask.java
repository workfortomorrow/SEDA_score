package stage.tasks;

import stage.stages.Stage;

/**
 * @Author: duhongjiang
 * @Date: Created in 2018/5/29
 */
public class DefaultTask implements Task {

    private   Stage stage;
    @Override
    public void setCurrentStage(Stage stage) {
        this.stage=stage;
    }

    @Override
    public void run() {

    }
}
