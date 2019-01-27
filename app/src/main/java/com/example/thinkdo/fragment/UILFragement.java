package com.example.thinkdo.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.thinkdo.compoentdemo.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.Random;

/**
 * Created by Administrator on 2016/7/1.
 */
public class UILFragement extends Fragment implements View.OnClickListener {
    ImageLoader imageLoader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageLoader = ImageLoader.getInstance();
        imageLoader.clearMemoryCache();
        imageLoader.clearDiskCache();
    }

    private String[] picUris = {
            "http://img5.hao123.com/data/1_ccb428d8820580a2108c94fb6c61f624_510",
            "http://img2.duitang.com/uploads/item/201202/05/20120205030238_NFhKx.jpg",
            "http://www.ahedu.gov.cn/uploads/image/20140513/20140513093034_15302.jpg",
            "http://e.hiphotos.baidu.com/image/pic/item/00e93901213fb80e3fb963da35d12f2eb9389456.jpg",
            "http://www.6518999.cn/imgall/oyys44l2n5xgkltdmm/pic/201605/01/22/29/572612dc61dbc517.jpg%21600x600.jpg",
            "http://img1.imgtn.bdimg.com/it/u=2164737755,2747118036&fm=21&gp=0.jpg"
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_uil, container, false);
        initView(view);
        return view;
    }


    private void initView(View view) {

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView1);
        if (imageView != null) imageView.setOnClickListener(this);

        imageView = (ImageView) view.findViewById(R.id.imageView2);
        if (imageView != null) imageView.setOnClickListener(this);

        imageView = (ImageView) view.findViewById(R.id.imageView3);
        if (imageView != null) imageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String uri = picUris[new Random().nextInt(6)];

        if (v.getId() == R.id.imageView1) {
            imageLoader.displayImage(uri, (ImageView) v, new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.ARGB_8888)
                    .displayer(new RoundedBitmapDisplayer(200))
                    .build());

        } else {
            imageLoader.displayImage(uri, (ImageView) v);
        }
    }

    private void log(String msg) {
        Log.d("TAG", msg);
    }

}
