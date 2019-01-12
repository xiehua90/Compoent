package com.example.thinkdo.model;


import java.util.List;

/**
 * Created by xh on 2018/3/15.
 */

public interface UploadView<T> {
    void initListView(List<T> list);
    void updateListView(List<T> list);
}
