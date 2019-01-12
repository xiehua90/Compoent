package com.example.thinkdo.adater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.example.thinkdo.compoentdemo.R;
import com.example.thinkdo.model.FuncNode;

import java.util.ArrayList;
import java.util.List;

public class FuncPreViewListAdapter extends BaseAdapter {
    List<FuncNode> dataList;

    public FuncPreViewListAdapter(FuncNode node) {
        dataList = new ArrayList<>();
        updateData(node);
    }

    public List<FuncNode> getDataList() {
        return dataList;
    }


    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_func_preview_list_item, parent, false);
        }

        FuncNode node = dataList.get(position);
        ((CheckBox) convertView).setText(node.getName());
        return convertView;
    }

    public void updateData(FuncNode node) {
        if (node.getChildren() == null || node.getChildren().isEmpty()) return;
        dataList.clear();
        dataList.addAll(node.getChildren());

        notifyDataSetChanged();
    }
}
