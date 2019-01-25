package com.example.thinkdo.model.dragger.zoo;


import dagger.Component;

@Component(modules = ZooModule.class)
@AnimalScope
public interface ZooComponent {
    Zoo maker();
}
