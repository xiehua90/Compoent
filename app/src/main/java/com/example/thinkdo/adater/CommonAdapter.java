package com.example.thinkdo.adater;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import java.util.List;

/**
 * Created by Administrator on 2016/7/11.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    private List<T> data;
    private Context context;
    private int layoutId;

    public CommonAdapter(Context context, List<T> data, int layoutId) {
        this.layoutId = layoutId;
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(context, convertView, parent, layoutId, position);
        bindData(viewHolder, getItem(position), position);
        return viewHolder.getConvertView();
    }

    public abstract void bindData(ViewHolder holder, T item, int pos);
}
