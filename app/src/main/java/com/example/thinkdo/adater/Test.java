package com.example.thinkdo.adater;

/**
 * Created by Administrator on 2016/7/25.
 */
public class Test {
    static {
        System.loadLibrary("cus");
    }

    public static void main(String[] args) {
        System.out.println(sayHello());
    }

    public static native String sayHello();
}
