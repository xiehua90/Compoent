package com.example.thinkdo;


import com.example.thinkdo.model.dragger.DaggerZooComponent;
import com.example.thinkdo.model.dragger.Zoo;

import org.junit.Test;


public class ExampleUnitTest {

    @Test
    public void daggerTest(){
        Zoo zoo = DaggerZooComponent.builder().build().maker();
        System.out.println(zoo.toString());

        zoo = DaggerZooComponent.create().maker();
        System.out.println(zoo.toString());
    }
}
