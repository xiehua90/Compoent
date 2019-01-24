package com.example.thinkdo.model.dragger;

import javax.inject.Inject;

public class Cooker /**implements CoffeeMaker*/ {
    String name;
    String coffeeKind;

//    @Inject
    public Cooker(String name, String coffeeKind) {
        this.name = name;
        this.coffeeKind = coffeeKind;
    }

    public String make(){
        return name + " make " + coffeeKind;
    }

//    @Override
//    public String makeCoffee() {
//        return name + " make " + coffeeKind;
//    }
}
