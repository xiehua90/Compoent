package com.example.thinkdo.fragment;

import android.app.Fragment;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import com.example.thinkdo.compoentdemo.R;
import com.example.thinkdo.view.GestureImageView;

public class GestureFragment extends Fragment {
    GestureImageView view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = new GestureImageView(getActivity());
        view.setImageResource(R.drawable.photoview);



//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                handler.sendEmptyMessage(0);
//                handler.post(this);
//            }
//        }, 2000);

//        test();
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        view.startDraw();
    }

    int index = 0;

    private int[] ivIds = new int[]{R.drawable.test1, R.drawable.test2, R.drawable.test3, R.drawable.test4, R.drawable.test5};

    Handler handler = new Handler(msg ->{
        view.setImageResource(ivIds[(index++)%5]);
        return true;
    });

    private void test() {
        RectF rect = new RectF(100, 0, 200, 100);

        Matrix matrix = new Matrix();
        matrix.setScale(2, 2, 100, 0);
//        matrix.postTranslate(-100, 0);

        matrix.mapRect(rect);

        Log.d("TAG", rect.toString());


        matrix.setTranslate(-50, 0);
        matrix.postScale(5, 5);

        float[] point = new float[]{100, 0};
        matrix.mapPoints(point);

        Log.d("TAG", point[0] + ", " + point[1]);
    }


}
