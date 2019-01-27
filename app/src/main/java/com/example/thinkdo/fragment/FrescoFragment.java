package com.example.thinkdo.fragment;

import android.app.Fragment;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thinkdo.compoentdemo.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;

/**
 * Created by Administrator on 2016/7/19.
 */
public class FrescoFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_fresco, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SimpleDraweeView image = (SimpleDraweeView) view.findViewById(R.id.my_image_view1);
        image.getHierarchy().setActualImageFocusPoint(new PointF(0.34f, 0.5f));
        image.setImageURI(Uri.parse("http://imgsrc.baidu.com/forum/pic/item/98c4cbfc1e178a8240d6a654f603738dab77e8bb.jpg"));

        image = (SimpleDraweeView) view.findViewById(R.id.my_image_view2);
        image.setImageURI(Uri.fromFile(new File("/storage/emulated/0/DCIM/Camera/IMG_20160720_171103.jpg")));

        image = (SimpleDraweeView) view.findViewById(R.id.my_image_view3);
        image.setImageURI(Uri.fromFile(new File("/storage/emulated/0/DCIM/Camera/IMG_20160720_171057.jpg")));
    }
}
