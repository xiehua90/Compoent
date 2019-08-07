//package com.example.thinkdo.model;
//
//import com.github.promeg.pinyinhelper.Pinyin;
//
///**
// * Created by xh on 2018/4/6.
// */
//
//public class VehicleBean {
//    private String name;
//    private String py;
//    private long key;
//    private int imageId;
//    private String firstLetter;
//
//    public VehicleBean(String name, long key, int imageId) {
//        setName(name);
//        this.key = key;
//        this.imageId = imageId;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//        if (name != null) {
//            this.py = Pinyin.toPinyin(name, "");
//            this.firstLetter = py.substring(0, 1);
//        } else {
//            this.py = "";
//            this.firstLetter = null;
//        }
//    }
//
//    public long getKey() {
//        return key;
//    }
//
//    public void setKey(long key) {
//        this.key = key;
//    }
//
//    public int getImageId() {
//        return imageId;
//    }
//
//    public void setImageId(int imageId) {
//        this.imageId = imageId;
//    }
//
//    public String getFirstLetter() {
//        return firstLetter;
//    }
//
//    public String getPy() {
//        return py;
//    }
//}
