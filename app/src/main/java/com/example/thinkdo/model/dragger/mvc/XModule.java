package com.example.thinkdo.model.dragger.mvc;


import com.example.thinkdo.model.dragger.CoffeeMaker;
import com.example.thinkdo.model.dragger.MachineMaker;
import com.example.thinkdo.model.dragger.MakerScope;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

@Module
public class XModule {
    MachineMaker maker;

//    @Inject
//    public XModule(MachineMaker maker){
//        this.maker = maker;
//        System.out.println("XModule()");
//    }
//
//    @MakerScope
//    @Provides
//    CoffeeMaker providerCooker(){
//        return maker;
//    }


    @Inject
    public XModule() {
        System.out.println("XModule()");
    }

    @MakerScope
    @Provides
    CoffeeMaker providerCooker() {
        return new MachineMaker();
    }


}
