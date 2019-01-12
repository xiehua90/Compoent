package com.example.thinkdo.adater;

import java.util.Random;

/**
 * Created by Administrator on 2016/7/25.
 */
public class Test {

    public String getString() {
        int random = new Random().nextInt();
        random *= random;
        return "random is" + random;
    }
}
