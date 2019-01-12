package com.example.thinkdo.model;

/**
 * Created by xh on 2018/3/15.
 */

public class UploadTask extends Object implements Task {
    private int uploadTask = Status_init;


    @Override
    public void perform(ProgressListener listener) {

    }

    @Override
    public void stopPerform() {

    }

    @Override
    public int getStatus() {
        return uploadTask;
    }

    @Override
    public long getLength() {
        long length = 0;
        for (FileInfo f: fileList){
            length += f.getSize();
        }
        return length;
    }

    @Override
    public long getProgressLength() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }
}
