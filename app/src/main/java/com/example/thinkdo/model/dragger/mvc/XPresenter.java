package com.example.thinkdo.model.dragger.mvc;


import com.example.thinkdo.model.dragger.MakerScope;

import javax.inject.Inject;

@MakerScope
public class XPresenter {
    public XActivity mActivity;

    @Inject
    XPresenter(XActivity activity){
        this.mActivity = activity;
    }

}
