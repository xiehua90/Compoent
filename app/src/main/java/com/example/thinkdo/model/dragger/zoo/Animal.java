package com.example.thinkdo.model.dragger.zoo;

import javax.inject.Inject;


public class Animal {
    String name;

    @Inject
    public Animal(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        System.out.println(name);
        return super.toString();
    }
}
