package com.example.thinkdo.model.dragger;

import javax.inject.Inject;
import javax.inject.Named;

public class CoffeeShop {
    CoffeeMaker maker;

    @Inject
    public CoffeeShop(@Named("James") CoffeeMaker maker) {
        this.maker = maker;
    }

    public void makeCoffee() {
        System.out.println(maker.makeCoffee()+ " "+toString());
    }
}
