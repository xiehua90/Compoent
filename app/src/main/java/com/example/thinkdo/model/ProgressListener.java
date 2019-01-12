package com.example.thinkdo.model;

/**
 * Created by xh on 2018/3/15.
 */

public interface ProgressListener {
    void taskStart(Task task);
    void taskProgress(Task task, long dataLength, long finishLength);
    void taskSuccess(Task task);
    void taskFailure(Task task);
}
