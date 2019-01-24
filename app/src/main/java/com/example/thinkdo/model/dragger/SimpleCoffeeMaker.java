package com.example.thinkdo.model.dragger;

import javax.inject.Inject;

public class SimpleCoffeeMaker implements CoffeeMaker {
    Cooker cooker;

    public SimpleCoffeeMaker(Cooker cooker){
        this.cooker = cooker;
    }

    @Override
    public String makeCoffee() {
        return cooker.make();
//        return "Coffee is made by SimpleMaker";
    }
}
