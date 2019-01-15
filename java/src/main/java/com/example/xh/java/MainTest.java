package com.example.xh.java;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainTest {

    public static void main(String[] args) {
        System.out.println("Hello World");

        Map<String, Integer> map = new HashMap<>();
        for (Map.Entry entry: map.entrySet()){
            entry.getValue();
            entry.getKey();

        }

        List<String> list = Arrays.asList("a","b","c");


    }
}
