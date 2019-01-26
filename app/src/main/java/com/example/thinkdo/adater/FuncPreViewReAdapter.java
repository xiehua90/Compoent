package com.example.thinkdo.adater;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thinkdo.compoentdemo.R;
import com.example.thinkdo.model.FuncNode;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class FuncPreViewReAdapter extends RecyclerView.Adapter<FuncPreViewReAdapter.MyViewHolder> {
    FuncNode mRootNode;
    ArrayList<FuncNode> dataList;
    private FuncPreViewReAdapterListener listener;

    public FuncPreViewReAdapter(FuncNode mRootNode) {
        dataList = new ArrayList<>();

        updateData(mRootNode);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frag_func_preview_recycler_item, null, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        FuncNode node = dataList.get(i);
        myViewHolder.onBindData(node);
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public void updateData(FuncNode rootNode) {
        if (rootNode == null || rootNode.getChildren() == null) return;
        dataList.clear();
        this.mRootNode = rootNode;
        dataList.addAll(mRootNode.getChildren());

        notifyDataSetChanged();
    }

    public void setListener(FuncPreViewReAdapterListener listener) {
        this.listener = listener;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView imageView;
        FuncNode node;


        public MyViewHolder(View view) {
            super(view);
            tv = view.findViewById(R.id.textView);
            imageView = view.findViewById(R.id.imageView);


            view.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemOnClick(node);
                }
            });
        }

        void onBindData(FuncNode node) {
            this.node = node;
            List children = node.getChildren();

            tv.setText(node.getName());
            imageView.setVisibility(children == null || children.isEmpty() ? View.GONE : View.VISIBLE);

        }


    }

    public interface FuncPreViewReAdapterListener {
        void onItemOnClick(FuncNode node);
    }
}
