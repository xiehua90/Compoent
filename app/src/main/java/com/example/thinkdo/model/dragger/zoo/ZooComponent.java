package com.example.thinkdo.model.dragger.zoo;


import com.example.thinkdo.model.dragger.mvc.XComponent;

import dagger.Component;

@AnimalScope
@Component(modules = ZooModule.class)
public interface ZooComponent {
    Zoo maker();


    void inject(Zoo zoo);


//    @Component.Builder
//    interface Builder{
//        ZooComponent build();
//    }
}
