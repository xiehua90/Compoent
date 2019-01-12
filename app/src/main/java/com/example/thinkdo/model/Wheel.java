package com.example.thinkdo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xh on 2018/3/31.
 */

public class Wheel implements Parcelable{
    int id;
    String name;

    public Wheel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    protected Wheel(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }

    public static final Creator<Wheel> CREATOR = new Creator<Wheel>() {
        @Override
        public Wheel createFromParcel(Parcel in) {
            return new Wheel(in);
        }

        @Override
        public Wheel[] newArray(int size) {
            return new Wheel[size];
        }
    };
}
