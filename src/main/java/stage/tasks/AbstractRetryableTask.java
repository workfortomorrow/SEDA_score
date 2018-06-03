package stage.tasks;

import stage.stages.RetryTaskWrapper;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: duhongjiang
 * @Date: Created in 2018/6/3
 */
public abstract class AbstractRetryableTask extends AbstractTask{

    private boolean retry;
    private AtomicInteger executeTimes = new AtomicInteger(-1);
    public boolean isRetry(){return retry;}
    public void setRetry(boolean retury){this.retry=retry;}

    //获取下一次等待时间
    protected abstract long getNextRetryDelay();
    //获取最大重试次数
    protected abstract int getMaxRetryTimes();


    @Override
    public void onTaskStart(){
        super.onTaskStart();
        executeTimes.getAndIncrement();
        setRetry(false);
    };

    @Override
    public void onTaskFailure(TaskException e){
        super.onTaskFailure(e);
        checkToRetry();
    };

    @Override
    public void onTaskSuccess(){
        super.onTaskSuccess();
        checkToRetry();
    };

    private void checkToRetry() {
        if(isRetry()){
            if(getRetryTimes()<getMaxRetryTimes()){
                this.getCurrentStage().getStageManager().getRetrySchduler().schedule(new RetryTaskWrapper(this)
                ,getNextRetryDelay(), TimeUnit.MICROSECONDS);
            }

        }else{
            if(logger!=null&&logger.isDebugEnabled()){
                logger.debug("retry times is 0 ",this);
            }
            onMaxRetryExceed();
        }
    }

    private void onMaxRetryExceed() {
    }


    public int getRetryTimes() {
        return executeTimes.intValue();
    }
}
