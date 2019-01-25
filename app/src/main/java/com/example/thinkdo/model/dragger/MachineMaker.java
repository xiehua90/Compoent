package com.example.thinkdo.model.dragger;

import javax.inject.Inject;

public class MachineMaker implements CoffeeMaker {

    @Inject
    public MachineMaker() {
    }

    @Override
    public String makeCoffee() {
        return "Coffee is made by SimpleMaker";
    }
}
