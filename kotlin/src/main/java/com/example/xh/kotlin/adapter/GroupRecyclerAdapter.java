package com.example.xh.kotlin.adapter;

import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class GroupRecyclerAdapter<S extends RecyclerView.ViewHolder, C extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<IndexPath> _index = new ArrayList<>();

    @Override
    public int getItemCount() {
        int count = 0;
        int sectionCount = getSectionCount();

        count += getSectionCount();

        _index.clear();
        for (int i = 0; i < sectionCount; i++) {
            _index.add(new IndexPath(i, -1));
            int rowCount = getChildCount(i);
            count += rowCount;

            for (int j = 0; j < rowCount; j++) {
                _index.add(new IndexPath(i, j));
            }
        }

        return count;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return viewType == 0 ? onCreateSectionViewHolder(parent) : onCreateChildViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        IndexPath indexPath = _index.get(position);

        if (indexPath.row == -1) {
            onBindSectionViewHolder((S) holder, indexPath.section);
        } else {
            onBindChildViewHolder((C) holder, indexPath);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return _index.get(position).row == -1 ? 0 : 1;
    }


    public abstract int getSectionCount();

    public abstract int getChildCount(int section);

    public abstract S onCreateSectionViewHolder(ViewGroup parent);

    public abstract C onCreateChildViewHolder(ViewGroup parent);

    public abstract void onBindSectionViewHolder(S holder, int section);

    public abstract void onBindChildViewHolder(C holder, IndexPath indexPath);


    public class IndexPath {
        IndexPath(int section, int row) {
            this.section = section;
            this.row = row;
        }

        public int section;
        public int row;
    }

}
