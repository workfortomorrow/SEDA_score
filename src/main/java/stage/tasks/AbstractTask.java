package stage.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stage.stages.Stage;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * @Author: duhongjiang
 * @Date: Created in 2018/5/29
 */
public abstract class AbstractTask implements Task,TaskContext {

    protected static Logger logger = LoggerFactory.getLogger(AbstractTask.class);
    private Map<String,Object> taskContext = new HashMap<>();
    private TaskCallback taskCallback =null;
    Stage stage;

    @Override
    public void setCurrentStage(Stage stage) {
        this.stage=stage;
    }

    @Override
    public void run() {
        onTaskStart();
        try{
            if(logger!=null&&logger.isTraceEnabled()){
                logger.trace(this.getClass().getSimpleName()+"  stage: "+this.stage.getName()+" is start.");
            }
            onRun();
            onTaskSuccess();
            if(logger!=null&&logger.isTraceEnabled()){
                logger.trace(this.getClass().getSimpleName()+"  stage: "+this.stage.getName()+"is success.");
            }
        }catch (Exception e){
            if(logger!=null&&logger.isDebugEnabled()){
                logger.debug(this.getClass().getSimpleName()+"  stage: "+this.stage.getName()+"is error."+e.getMessage());
            }
            onTaskFailure(new TaskException(e));
        }finally {
            getCurrentStage().taskComplete();
            onTaskFinished();
            if(logger!=null&&logger.isTraceEnabled()){
                logger.trace(this.getClass().getSimpleName()+"  stage: "+this.stage.getName()+"is success.");
            }
        }
    }

    protected void onTaskFinished(){};

    protected void onTaskFailure(TaskException e){};

    protected void onTaskSuccess(){};

    protected void onRun() { };

    protected  void onTaskStart(){};

    @Override
    public void setAttribute(String name, Object value) {

    }

    @Override
    public <T> T getAttribute(String name, Class<T> classz) {
        return null;
    }

    @Override
    public Collection<String> getAttibutesName() {
        return null;
    }

    public Stage getCurrentStage() {
        return stage;
    }
}
