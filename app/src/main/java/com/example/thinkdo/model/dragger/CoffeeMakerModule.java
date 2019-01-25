package com.example.thinkdo.model.dragger;


import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class CoffeeMakerModule {
    @Provides
    @MakerScope
    @Named("James")
    CoffeeMaker provideJamesCooker() {
        return new Cooker("James", "Espresso");
    }

    @Provides
    @Named("Adala")
    CoffeeMaker provideAdalaCooker() {
        return new Cooker("Adala", "雀巢");
    }


}
