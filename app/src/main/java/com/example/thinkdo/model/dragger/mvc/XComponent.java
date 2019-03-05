package com.example.thinkdo.model.dragger.mvc;

import com.example.thinkdo.model.dragger.MachineMaker;
import com.example.thinkdo.model.dragger.MakerScope;

import dagger.BindsInstance;
import dagger.Component;

@MakerScope
@Component(modules = XModule.class)
public interface XComponent {

    void inject(XActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        XComponent.Builder view(XActivity activity);
        XComponent build();
    }
}
