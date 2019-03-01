package com.example.thinkdo;


import com.example.thinkdo.model.dragger.CoffeeMakerModule;
import com.example.thinkdo.model.dragger.CoffeeShopComponent;
import com.example.thinkdo.model.dragger.DaggerCoffeeShopComponent;
import com.example.thinkdo.model.dragger.zoo.DaggerZooComponent;
import com.example.thinkdo.model.dragger.zoo.ZooComponent;
import com.example.thinkdo.model.dragger.zoo.ZooModule;

import org.junit.Assert;
import org.junit.Test;


import java.util.Arrays;
import java.util.List;
import java.util.Observable;


public class ExampleUnitTest {

    @Test
    public void zooDaggerTest() {
//        ZooComponent ZooComponent = DaggerZooComponent.builder().zooModule(new ZooModule()).build();
//        ZooComponent.maker().toString();
//        ZooComponent.maker().toString();
//        DaggerZooComponent.builder().zooModule(new ZooModule()).build().maker().toString();

//        ZooModule zooModule = new ZooModule();
//        List<String> list = Arrays.asList("Lion", "Tiger", " Monkey");
//        for (String name : list) {
//            zooModule.setName(name);
//            DaggerZooComponent.builder().zooModule(zooModule).build().maker().toString();
//        }


    }

    @Test
    public void CoffeeTest() {
        CoffeeShopComponent shopComponent = DaggerCoffeeShopComponent.builder().coffeeMakerModule(new CoffeeMakerModule()).build();
        shopComponent.maker().makeCoffee();
        shopComponent.maker().makeCoffee();
    }


    @Test
    public void zooTest(){
        String str  = DaggerZooComponent.builder().build().maker().toString();
        Assert.assertNotNull(str);
    }
}
