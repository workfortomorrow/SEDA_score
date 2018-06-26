package stage.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stage.stages.DefaultStage;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池 请求数 大于 最大线程数+队列大小
 * @Author: duhongjiang
 * @Date: Created in 2018/6/24
 */
public abstract class MyRejectedExecutionHandler implements RejectedExecutionHandler {

    private static Logger logger = LoggerFactory.getLogger(MyRejectedExecutionHandler.class);
    @Override
    public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
//        threadPoolExecutor.g

          rejected();
    }

    protected abstract void rejected();
}
