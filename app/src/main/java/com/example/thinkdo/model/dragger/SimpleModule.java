package com.example.thinkdo.model.dragger;

import dagger.Module;
import dagger.Provides;

public class SimpleModule {
    Cooker provideCooker() {
        return new Cooker("James", "Espresso");
    }
}
