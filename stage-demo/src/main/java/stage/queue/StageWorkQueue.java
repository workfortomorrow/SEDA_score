package stage.queue;

import stage.tasks.Task;

import java.util.LinkedList;

/**
 * @Author: duhongjiang
 * @Date: Created in 2018/6/25
 */
public class StageWorkQueue {

//    任务队列
    private LinkedList<Task> tasksQueue = new LinkedList<Task>();
//    队列大小
    private int queueSize;
//     队列当前状态
    private long queueStatus;


    public void postTask(Task task){
        synchronized (tasksQueue){
            if (queueSize == tasksQueue.size()){
                //logger queue is fuall

                //返回错误状态
            }

            try{
                tasksQueue.add(task);
                tasksQueue.notify();
            }catch (Exception e){
                //logger  exception while put queue

                //返回错误状态
            }

        }
        // 返回成功状态
    }


}
