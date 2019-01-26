package com.example.thinkdo.model;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

public class FuncPreviewViewModel extends ViewModel {

    public FuncNode mRootNode;
    public MediatorLiveData<FuncNode> displayLiveData;

    public FuncPreviewViewModel() {
        getData();
        displayLiveData = new MediatorLiveData<>();
        displayLiveData.setValue(mRootNode);
    }


    private void getData() {
        int nodeSize = 50;
        ArrayList<FuncNode> list = new ArrayList<>();
        SparseArray<FuncNode> map = new SparseArray<>();

        for (int i = 0; i < nodeSize; i++) {
            FuncNode funcNode = new FuncNode(i, -1, "element: " + i);
            list.add(funcNode);
            map.put(i, funcNode);
        }

        ArrayList<FuncNode> parentList = new ArrayList<>();
        Random random = new Random();


        Iterator<FuncNode> iterator = list.iterator();
        while (iterator.hasNext()) {
            FuncNode node = iterator.next();
            int size = parentList.size();
            if (size != 0) {
                int pos = random.nextInt(size);
                int parentId = parentList.get(pos).getId();
                node.setParentId(parentId);
            }
            parentList.add(node);
        }

        for (FuncNode node : list) {
            FuncNode parent = map.get(node.getParentId());
            if (parent != null) {
                parent.addChild(node);
                node.setParent(parent);
            }
        }

        mRootNode = map.get(0);
    }
}
