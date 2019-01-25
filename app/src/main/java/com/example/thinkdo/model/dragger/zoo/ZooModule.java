package com.example.thinkdo.model.dragger.zoo;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ZooModule {
    String name;

    public void setName(String name) {
        this.name = name;
    }

    @Provides
    @Named("Elephant")
    Animal provideElephant() {
        return new Animal("Elephant");
    }

    @Provides
    @AnimalScope
    @Named("Panda")
    Animal providePanda() {
        return new Animal("Panda");
    }

    @Provides
    @Named("Animal")
    public Animal provideAnimal() {
        return new Animal(name);
    }
}
