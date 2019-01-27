package com.example.thinkdo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thinkdo.compoentdemo.R;

/**
 * Created by Administrator on 2016/4/26.
 */
public class WindowParamsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_window_params, container, false);
        float density = getActivity().getResources().getDisplayMetrics().density;
        float densityDpi = getActivity().getResources().getDisplayMetrics().densityDpi;
        float densityScale = getActivity().getResources().getDisplayMetrics().scaledDensity;


        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        int height = dm.heightPixels;
        int width = dm.widthPixels;
        int dp = getResources().getConfiguration().smallestScreenWidthDp;


        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(String.format("height=%d, width=%d, density=%f, densityDpi=%f, densityScale=%f, smallestScreenWidthDp=%d,", height, width, density, densityDpi, densityScale, dp));


        height = getResources().getDisplayMetrics().heightPixels;
        width = getResources().getDisplayMetrics().widthPixels;

        textView = (TextView) view.findViewById(R.id.textView1);

        int wDp = getResources().getConfiguration().screenWidthDp;
        int hDp = getResources().getConfiguration().screenHeightDp;
        textView.setText(String.format("height=%d, width=%d, density=%f, screenWidthDp=%d, screenHeightDp=%d", height, width, density, dp, wDp, hDp));
        return view;
    }
}
