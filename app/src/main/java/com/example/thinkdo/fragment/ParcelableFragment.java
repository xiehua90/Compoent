package com.example.thinkdo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thinkdo.model.Vehicle;
import com.example.thinkdo.model.Wheel;

import java.util.ArrayList;

/**
 * Created by xh on 2018/3/31.
 */

public class ParcelableFragment extends Fragment {
    final String WHEEL = "WHEEL";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle saveBundle) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(WHEEL, onCreateVehicle());
        Vehicle vehicle = bundle.getParcelable(WHEEL);
        Log.d(WHEEL, vehicle.toString());
        return super.onCreateView(inflater, container, saveBundle);
    }

    private Vehicle onCreateVehicle() {
        ArrayList<Wheel> wheels = new ArrayList<>();

        wheels.add(new Wheel(1, "I am wheel one"));
        wheels.add(new Wheel(2, "I am wheel two"));


        return new Vehicle(1, "vehicle", wheels, null);
    }
}
