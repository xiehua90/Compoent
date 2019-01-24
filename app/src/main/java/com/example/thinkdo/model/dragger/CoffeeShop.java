package com.example.thinkdo.model.dragger;

import javax.inject.Inject;

public class CoffeeShop {
    CoffeeMaker maker;

    public CoffeeShop(CoffeeMaker maker) {
        this.maker = maker;
    }

    public void makeCoffee() {
        System.out.println(maker.makeCoffee());
    }
}
