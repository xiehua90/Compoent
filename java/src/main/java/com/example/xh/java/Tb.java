package com.example.xh.java;


import javax.inject.Inject;


public class Tb {
    @Inject
    DragClass maker;

    @Inject
    public Tb(){}

    @Override
    public String toString() {
        return maker.toString();
    }
}



