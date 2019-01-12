package com.example.thinkdo.fragment;

import android.app.Fragment;
import android.gesture.Gesture;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.thinkdo.adater.CollapseFraAdapter;
import com.example.thinkdo.compoentdemo.R;
import com.example.thinkdo.view.CollapseTextView;

/**
 * Created by xh on 2018/4/14.
 */

public class CollapseTextFragment extends Fragment {
    CollapseTextView tv;
    EditText et, et2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        RecyclerView view = new RecyclerView(getActivity());
        view.setLayoutManager(new LinearLayoutManager(getActivity()));
        view.setAdapter(new CollapseFraAdapter());
        view.setBackgroundColor(Color.RED);


//        GestureDetector detector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener());



        return view;


//        View view = inflater.inflate(R.layout.frag_auto_fit_text, null);
//        tv = (CollapseTextView) view.findViewById(R.id.tv);
//        et = (EditText) view.findViewById(R.id.editText);
//        et2 = (EditText) view.findViewById(R.id.editText2);
////        tv.setText("Hello");
//        et.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                tv.setText(s.toString());
//            }
//        });
//
//        et2.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                int i = 0;
//                try {
//                    i = Integer.parseInt(s.toString());
//                } catch (NumberFormatException e) {
//
//                }
//
//                if (i < 0) {
//                    i = 0;
//                } else if (i > 3) {
//                    i = 3;
//                }
//
//                tv.setCollapseState(i);
//                tv.requestLayout();
//            }
//        });
//
//        return view;

    }


}
