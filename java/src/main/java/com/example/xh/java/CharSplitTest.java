package com.example.xh.java;

import java.util.Arrays;
import java.util.List;

public class CharSplitTest {
    public static void main(String[] args) {
        String msg = "开好车就是好人了?如果是的话，那么想做好人的是不是都得买辆好车了呢?";
        List<String> keys = Arrays.asList("如果", "想做好人", "买");

        int lastIndex = 0;
        StringBuilder builder = new StringBuilder();
        for (String key : keys) {
            int s = msg.indexOf(key);
            builder.append(msg.subSequence(lastIndex, s));
            builder.append("<" + key + "/>");
            lastIndex = s + key.length();
        }
        builder.append(msg.subSequence(lastIndex, msg.length()));
        System.out.println(builder.toString());

        System.out.print(String.format("<a href=\"%s\">%s</a>","http://www.baidu.com","http://www.baidu.com"));
    }
}
