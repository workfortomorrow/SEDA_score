package stage.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: duhongjiang
 * @Date: Created in 2018/6/24
 */
public class NamedThreadFactory implements ThreadFactory {

    private String name ;
    private AtomicInteger counter =new AtomicInteger(0);

    public NamedThreadFactory(String name){
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable runnable) {

        Thread thread =new Thread(runnable);
        thread.setName(name + "_"+counter.incrementAndGet());
        return thread;
    }
}
