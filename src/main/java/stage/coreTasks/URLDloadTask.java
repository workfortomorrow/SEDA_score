package stage.coreTasks;

import stage.StageContains;
import stage.tasks.AbstractRetryableTask;
import stage.tasks.TaskException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Author: duhongjiang
 * @Date: Created in 2018/6/4
 */
public class URLDloadTask extends AbstractRetryableTask {

    private String url;

    public URLDloadTask(String url){
        this.url=url;
    }

    @Override
    protected long getNextRetryDelay() {
        return 50;
    }

    @Override
    protected int getMaxRetryTimes() {
        return 5;
    }

    @Override
    protected void doRun()throws TaskException {

        try {

            URL requestUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setDoInput(true);
            InputStream inputStream = connection.getInputStream();
            connection.connect();

            DataParsingTask dataParsingTask =new DataParsingTask(inputStream);

            //向前触发 段 任务
            forward(StageContains.PARSE_TASK,dataParsingTask);
            System.out.println("Finished url :"+url);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            setRetry(true);
            e.printStackTrace();
            throw new TaskException(e);
        }
    }
}
