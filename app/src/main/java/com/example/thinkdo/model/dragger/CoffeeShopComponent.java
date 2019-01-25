package com.example.thinkdo.model.dragger;

import dagger.Component;

@MakerScope
@Component(modules = CoffeeMakerModule.class)
public interface CoffeeShopComponent {
    CoffeeShop maker();
}
