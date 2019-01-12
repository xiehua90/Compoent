package com.example.thinkdo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xh on 2018/3/15.
 */

public class UploadPresenter {

    private UploadView<UploadTask> view;
    private List<UploadTask> data = new ArrayList<>();

    public UploadPresenter(UploadView<UploadTask> uploadView){
        this.view = uploadView;
    }

    public void addTask(){

    }

    public void deleTask(){

    }

    public void init(){
        view.initListView(data);
    }




}
