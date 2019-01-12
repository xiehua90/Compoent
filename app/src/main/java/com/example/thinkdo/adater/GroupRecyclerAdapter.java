package com.example.thinkdo.adater;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

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
