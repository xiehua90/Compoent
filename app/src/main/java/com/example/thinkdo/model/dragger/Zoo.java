package com.example.thinkdo.model.dragger;

import javax.inject.Inject;

public class Zoo {
    @Inject Animal animal;

    @Inject
    Zoo(){
    }

//    @Inject
//    Zoo(Animal animal){
//        this.animal = animal;
//    }

    @Override
    public String toString() {
        System.out.println(animal.toString() + super.toString());
        return super.toString();
    }
}
