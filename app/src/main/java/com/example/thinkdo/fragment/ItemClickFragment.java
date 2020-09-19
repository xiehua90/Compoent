package com.example.thinkdo.fragment;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thinkdo.adater.CommonAdapter;
import com.example.thinkdo.adater.ViewHolder;
import com.example.thinkdo.compoentdemo.LeakActivity;
import com.example.thinkdo.compoentdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/11.
 */
public class ItemClickFragment extends ListFragment {
    List<String> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        list = new ArrayList<String>() {
            {
                add("Hello");
                add("World");
                add("Nice to meet you");
                add("good morning");
                add("Hello");
                add("World");
                add("Nice to meet you");
                add("good morning");
                add("Hello");
                add("World");
                add("Nice to meet you");
                add("good morning");
                add("Hello");
                add("World");
                add("Nice to meet you");
                add("good morning");
            }
        };


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListAdapter(new CommonAdapter<String>(getActivity(), list, R.layout.item_listview) {
            @Override
            public void bindData(ViewHolder holder, String item,int pos) {
                TextView tv = (TextView) holder.getView(R.id.textView);
                Button btn = (Button) holder.getView(R.id.btn);
                btn.setTag(pos);
                tv.setText(item);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = (int) v.getTag();
                        Toast.makeText(getActivity(), "button "+i, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getActivity(), "p: " + position, Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getActivity(), LeakActivity.class));
            }
        });
    }
}
