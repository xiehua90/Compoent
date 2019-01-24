package com.example.thinkdo.model.dragger;

import javax.inject.Inject;


public class Animal {
    String name;

    @Inject
    public Animal(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
