package com.example.thinkdo.fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import com.example.thinkdo.application.MainApplication;
import com.example.thinkdo.compoentdemo.R;

/**
 * Created by Administrator on 2016/7/21.
 */
public class GlideFragment extends Fragment implements View.OnClickListener {
    PhotoView imageView;
    String[] paths = {
            "http://imgsrc.baidu.com/forum/pic/item/98c4cbfc1e178a8240d6a654f603738dab77e8bb.jpg",
            "http://img4.imgtn.bdimg.com/it/u=1711694327,4272349713&fm=206&gp=0.jpghttp://img4.imgtn.bdimg.com/it/u=1711694327,4272349713&fm=206&gp=0.jpg",
            "http://pic1cn.xingyun.cn/media/users/post/010/58/100200886227_105805.jpg",
            "http://img5.imgtn.bdimg.com/it/u=1398210878,2119176063&fm=206&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=2413941371,289721657&fm=206&gp=0.jpg"
    };

    int i = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        imageView = new PhotoView(getActivity());
        imageView.enable();
        imageView.setOnClickListener(this);
        imageView.performClick();
        return imageView;
    }

    @Override
    public void onClick(View v) {
//        Glide.with(this)
//                .load(Uri.parse(paths[i++ % 5]))
//                .placeholder(R.drawable.ic_sample_test)
//                .crossFade()
//                .transform(new MyTransformation(getActivity()))
//                .into(imageView);
    }

//
//    class MyTransformation extends BitmapTransformation {
//        int margin = 4;
//
//        public MyTransformation(Context context) {
//            super(context);
//        }
//
//        @Override
//        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
//            margin = (int) (margin * MainApplication.density);
//            Bitmap bitmap = TransformationUtils.fitCenter(toTransform, pool, outWidth, outHeight);
//            Canvas canvas = new Canvas(bitmap);
//            Bitmap mark = BitmapFactory.decodeResource(getResources(), R.drawable.watermark);
//            canvas.drawBitmap(mark, bitmap.getWidth() - mark.getWidth() - margin, bitmap.getHeight() - mark.getHeight() - margin, null);
//            return bitmap;
//        }
//
//        @Override
//        public String getId() {
//            return "com.example.thinkdo.fragment.MyTransformation";
//        }
//    }
}
