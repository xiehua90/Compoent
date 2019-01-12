package com.example.thinkdo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xh on 2018/3/15.
 */

public interface Task {
    int Status_init = 0;
    int Status_progress = 1;
    int Status_finish = 2;
    int Status_cancel = -1;

    List<FileInfo> fileList = new ArrayList<>();

    void perform(ProgressListener listener);
    void stopPerform();

    int getStatus();

    long getLength();

    long getProgressLength();

    String getName();

}
