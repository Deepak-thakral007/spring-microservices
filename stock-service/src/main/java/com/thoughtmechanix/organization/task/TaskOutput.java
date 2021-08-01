package com.thoughtmechanix.organization.task;
import java.util.Map;
public class TaskOutput implements Result {

    private final boolean success;

    private Map<String,Object> contextMap ;

    private final Exception taskException ;

    public boolean isSuccess() {
        return success;
    }

    public Map<String, Object> getContextMap() {
        return contextMap;
    }

    public Exception getTaskException() {
        return taskException;
    }

    public Object getResult() {
        return result;
    }

    private final Object result ;

    public TaskOutput(boolean success, Map<String, Object> contecxMap, Exception taskException, Object result) {
        this.success = success;
        this.contextMap =contecxMap;
        this.taskException = taskException;
        this.result = result;
    }
}
