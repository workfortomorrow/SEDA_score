package stage.tasks;

import java.util.Collection;
import java.util.List;

/**
 * @Author: duhongjiang
 * @Date: Created in 2018/5/29
 */
public interface TaskContext {

    public void setAttribute(String name,Object value);
    public <T> T getAttribute(String name,Class<T> classz);
    public Collection<String> getAttibutesName();

}
