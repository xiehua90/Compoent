package com.example.thinkdo.fragment;

import android.app.Fragment;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thinkdo.compoentdemo.R;

/**
 * Created by Administrator on 2016/5/27.
 */
public class ScrollFragment extends Fragment {
    int position = 1;
    LinearLayout linear;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scroll, container, false);
        linear = (LinearLayout) view.findViewById(R.id.linearLayout);
        TextView tv = (TextView) view.findViewById(R.id.textView);
        tv.setTextColor(new ColorStateList(new int[][]{
                {-android.R.attr.state_pressed}, {android.R.attr.state_pressed}
        }, new int[]{Color.BLACK, Color.RED}));
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = new TextView(getActivity());
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(Color.RED);
                tv.setText(String.format("position:%d", position++));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                linear.addView(tv, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
                Toast.makeText(getActivity(), String.format("count:%d, height:%d", linear.getChildCount(), linear.getMeasuredHeight()), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
