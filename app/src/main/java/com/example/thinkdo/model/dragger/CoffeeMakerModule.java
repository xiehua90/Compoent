package com.example.thinkdo.model.dragger;


import com.example.thinkdo.model.dragger.zoo.Details;

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
    @MakerScope
    @Named("Adala")
    CoffeeMaker provideAdalaCooker() {
        return new Cooker("Adala", "雀巢");
    }


    @Provides
    @MakerScope
    CoffeeMaker provideACoffeeMaker() {
        return new Cooker("Coffee", "雀巢");
    }


    @Provides
    @MakerScope
    MachineMaker provideMachineMaker() {
        return new MachineMaker();
    }

    @Provides
    @MakerScope
    Cooker provideCooker() {
        return new Cooker("Adala <provides>", "雀巢");
    }

    @Provides
    @MakerScope
    @Named("details")
    Details provideDetails(){
        return new Details("I am detail");
    }

    @Provides
    @MakerScope
    @Named("details2")
    Details provideDetails2(){
        return new Details("I am detail--2");
    }


}
