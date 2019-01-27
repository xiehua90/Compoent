package com.example.thinkdo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.thinkdo.compoentdemo.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/6.
 */
public class ListViewFragment extends Fragment {
    private ArrayAdapter<String> adapter;
    private ArrayList<String> list = new ArrayList<>();
    private int index = 0;
    private Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        initData();
        final ListView listView = (ListView) view.findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        TextView tv = new TextView(getActivity());
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        tv.setText("hello World");
        ImageView iv = new ImageView(getActivity());
        iv.setImageResource(R.drawable.ic_mark);
        iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
        listView.addFooterView(tv);
        listView.addHeaderView(iv);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
                adapter.notifyDataSetChanged();
            }
        }, 10000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        }, 20000);

        return view;
    }

    private void initData() {
        for (int i = index; i < index + 20; i++) {
            list.add(String.valueOf(i));
        }
        index += 20;
    }
}
