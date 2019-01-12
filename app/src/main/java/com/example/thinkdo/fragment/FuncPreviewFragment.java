package com.example.thinkdo.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.example.thinkdo.adater.FuncPreViewListAdapter;
import com.example.thinkdo.adater.FuncPreViewReAdapter;
import com.example.thinkdo.compoentdemo.R;
import com.example.thinkdo.model.FuncNode;
import com.example.thinkdo.model.FuncPreviewViewModel;


public class FuncPreviewFragment extends Fragment {
    private RecyclerView recyclerView;
    private ListView listView;
    private EditText editText;
    private FuncPreviewViewModel mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(FuncPreviewFragment.this).get(FuncPreviewViewModel.class);
        mViewModel.displayLiveData.observe(this, node -> {
            initListView(node);
            initRecyclerView(node);
        });

        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                FuncNode parent = mViewModel.displayLiveData.getValue().getParent();
                if (parent != null) {
                    mViewModel.displayLiveData.setValue(parent);
                } else {
                    getActivity().finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_func_preview, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        listView = view.findViewById(R.id.listView);
        editText = view.findViewById(R.id.et_search);

        view.findViewById(R.id.btn_cancel).setOnClickListener(v -> {
            if (!editText.getText().toString().equals("")) editText.setText("");
        });
    }

    FuncPreViewReAdapter recyclerAdapter;
    FuncPreViewListAdapter listAdapter;

    private void initRecyclerView(FuncNode node) {
        if (recyclerAdapter == null) {
            recyclerAdapter = new FuncPreViewReAdapter(node);
            recyclerAdapter.setListener(n -> {
                if (n.getChildren() != null && !n.getChildren().isEmpty()) {
                    mViewModel.displayLiveData.setValue(n);
                }
            });
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);


            recyclerView.setAdapter(recyclerAdapter);
            recyclerView.setLayoutManager(gridLayoutManager);
        } else {
            recyclerAdapter.updateData(node);
        }
    }

    private void initListView(FuncNode node) {
        if (listAdapter == null) {
            listAdapter = new FuncPreViewListAdapter(node);
            listView.setAdapter(listAdapter);
        } else {
            listAdapter.updateData(node);
        }
    }
}
