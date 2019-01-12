package com.example.thinkdo.model;

/**
 * Created by xh on 2018/4/14.
 */

public class CollapseBean {
    private String text;
    private int state;

    public CollapseBean(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public interface CollapseState {
        int Init = 0;
        int Less = 1;
        int More = 2;
        int Collapse = 3;
    }
}
