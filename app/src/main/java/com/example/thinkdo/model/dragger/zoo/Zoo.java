package com.example.thinkdo.model.dragger.zoo;

import javax.inject.Inject;
import javax.inject.Named;

public class Zoo {
//    @Inject
//    @Named("Animal")
//    Animal animal;

    @Inject
    Zoo() {
    }

//    @Inject
//    Zoo(Animal animal){
//        this.animal = animal;
//    }

    @Override
    public String toString() {
//        System.out.println(animal.toString() + "  " + super.toString());
        System.out.print("ZOO Hello");
        return super.toString();
    }
}
