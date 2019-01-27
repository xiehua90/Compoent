package com.example.thinkdo.model;


import androidx.annotation.NonNull;

/**
 * Created by xh on 2018/3/5.
 */

public class FileInfo extends Object implements Comparable<FileInfo> {
    private String filePath;
    private String fileName;

    private String relativePath;

    private long size = 0;

    private boolean isDir = false;
    private boolean isChecked = false;

    private boolean isVisible = false;

    public FileInfo(){

    }

    public FileInfo(String filePath, String fileName, long size, boolean isDir){
        this.filePath = filePath;
        this.fileName = fileName;
        this.size = size;
        this.isDir = isDir;
    }

    @Override
    public int compareTo(@NonNull FileInfo o) {
        return fileName.compareTo(o.fileName);
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public boolean isDir() {
        return isDir;
    }

    public void setDir(boolean dir) {
        isDir = dir;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

}
