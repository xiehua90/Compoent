package com.example.thinkdo.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by xh on 2018/3/31.
 */

public class Vehicle implements Parcelable {
    int id;
    String name;
    ArrayList<Wheel> wheels;
    ArrayList<Seat> seats;

    public Vehicle(int id, String name, ArrayList<Wheel> wheels, ArrayList<Seat> seats) {
        this.id = id;
        this.name = name;
        this.wheels = wheels;
        this.seats = seats;
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

    public ArrayList<Wheel> getWheels() {
        return wheels;
    }

    public void setWheels(ArrayList<Wheel> wheels) {
        this.wheels = wheels;
    }

    public ArrayList<Seat> getSeats() {
        return seats;
    }

    public void setSeats(ArrayList<Seat> seats) {
        this.seats = seats;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeTypedList(wheels);
    }

    protected Vehicle(Parcel in) {
        id = in.readInt();
        name = in.readString();
        wheels = in.createTypedArrayList(Wheel.CREATOR);
    }

    public static final Creator<Vehicle> CREATOR = new Creator<Vehicle>() {
        @Override
        public Vehicle createFromParcel(Parcel in) {
            return new Vehicle(in);
        }

        @Override
        public Vehicle[] newArray(int size) {
            return new Vehicle[size];
        }
    };
}
