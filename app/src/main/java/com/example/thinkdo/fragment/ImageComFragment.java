package com.example.thinkdo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bm.library.PhotoView;
import com.example.thinkdo.compoentdemo.R;


/**
 * Created by Administrator on 2016/6/29.
 */
public class ImageComFragment extends Fragment {
    PhotoView photoView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        photoView = (PhotoView) view.findViewById(R.id.iv_photo);
        photoView.enable();
        return view;
    }


}
