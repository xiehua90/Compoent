package com.example.thinkdo.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.thinkdo.adater.Test;
import com.example.thinkdo.compoentdemo.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by xh on 2018/5/1.
 */

public class ExpandListFragment extends BaseFragment {
    @BindView(R.id.expandListView)
    ExpandableListView expandListView;
    @BindView(R.id.textView)
    TextView tv;


    @Override
    public int getLayoutId() {
        return R.layout.frag_expand_list_view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        expandListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View view, int position, int childPosition, long id) {
//                int[] loc = new int[2];
//                view.getLocationInWindow(loc);
//                Log.d("TAG", "pos=" + position + " width=" + loc[0] + " height=" + loc[1]);
//
//                int pos = position - expandListView.getFirstVisiblePosition();
//                View item = expandListView.getChildAt(pos);
//                item.getLocationInWindow(loc);
//                Log.d("TAG", "pos=" + pos + " width=" + loc[0] + " height=" + loc[1]);

                return false;
            }
        });

        expandListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                int[] loc = new int[2];
//                view.getLocationInWindow(loc);
//                Log.d("TAG", "pos=" + position + " width=" + loc[0] + " height=" + loc[1]);
//
//                int pos = position - expandListView.getFirstVisiblePosition();
//                View item = expandListView.getChildAt(pos);
//                item.getLocationInWindow(loc);
//                Log.d("TAG", "pos=" + pos + " width=" + loc[0] + " height=" + loc[1]);
            }
        });

        tv.setText(Test.sayHello());
    }

    @OnClick({R.id.btn})
    public void onClick(View view) {
        expandListView.setAdapter(new MyBaseAdapter());
        view.setVisibility(View.INVISIBLE);
    }

    class MyBaseAdapter extends BaseExpandableListAdapter {
        @Override
        public int getGroupCount() {
            return 10;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return 5;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return null;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return null;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LinearLayout.inflate(parent.getContext(), android.R.layout.simple_expandable_list_item_1, null);
            }
            TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
            tv.setText("组: " + groupPosition);

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LinearLayout.inflate(parent.getContext(), android.R.layout.simple_expandable_list_item_2, null);
            }
            TextView tv1 = (TextView) convertView.findViewById(android.R.id.text1);
            tv1.setText("A " + childPosition);
            TextView tv2 = (TextView) convertView.findViewById(android.R.id.text2);
            tv2.setText("B: " + childPosition);

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
