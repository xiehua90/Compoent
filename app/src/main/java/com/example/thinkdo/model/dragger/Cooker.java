package com.example.thinkdo.model.dragger;

import javax.inject.Inject;

public class Cooker implements CoffeeMaker {
    String name;
    String coffeeKind;

    @Inject
    public Cooker(String name, String coffeeKind) {
        this.name = name;
        this.coffeeKind = coffeeKind;
        System.out.println("Cooker initialized");
    }

    @Override
    public String makeCoffee() {
        return coffeeKind + " Coffee is made by " + name + " Cooker";
    }
}
