package com.example.thinkdo.adater;

import android.view.ViewGroup;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by xh on 2018/4/7.
 */

public class GroupRecyclerAdapter extends RecyclerView.Adapter {
    List itemData;
    List<String> groupData;


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        int count = itemData == null ? 0 : itemData.size();
        count += (groupData == null ? 0 : groupData.size());
        return count;
    }
}
