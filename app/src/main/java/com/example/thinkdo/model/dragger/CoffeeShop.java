package com.example.thinkdo.model.dragger;

import com.example.thinkdo.model.dragger.zoo.Details;

import javax.inject.Inject;
import javax.inject.Named;

public class CoffeeShop {
    @Inject
    @Named("Adala")
    CoffeeMaker maker;

    @Inject
    Cooker cooker;

    @Inject
    MachineMaker machineMaker;

    Details details;

//    @Inject
//    MachineMaker machineMaker;

//    @Inject
//    public CoffeeShop(@Named("James") CoffeeMaker maker) {
//        this.maker = maker;
//    }

//    @Inject
//    public CoffeeShop(CoffeeMaker maker) {
//        this.maker = maker;
//    }

    @Inject
    public CoffeeShop(@Named("details") Details details) {
        this.details = details;
    }


    public void makeCoffee() {
        System.out.println(maker.makeCoffee() + " " + toString() + " " + details.toString());
        System.out.println(cooker.makeCoffee() + "  " + toString());
    }
}
