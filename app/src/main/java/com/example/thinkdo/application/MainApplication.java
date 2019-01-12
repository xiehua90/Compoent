package com.example.thinkdo.application;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.thinkdo.compoentdemo.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

/**
 * Created by Administrator on 2016/7/1.
 */
public class MainApplication extends Application {
    public static float density;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(getApplicationContext());
        density = getResources().getDisplayMetrics().density;
        final int margin = (int) (4 * density);
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .preProcessor(new BitmapProcessor() {
                    @Override
                    public Bitmap process(Bitmap src) {
                        if (src == null) return null;
                        Bitmap bitmap = src.copy(Bitmap.Config.ARGB_8888, true);
                        Canvas canvas = new Canvas(bitmap);
                        Bitmap waterMark = BitmapFactory.decodeResource(getResources(), R.drawable.watermark);
                        canvas.drawBitmap(waterMark, bitmap.getWidth() - waterMark.getWidth() - margin, bitmap.getHeight() - waterMark.getHeight() - margin, null);
                        return bitmap;
                    }
                })
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(500, 500)
                .diskCacheExtraOptions(500, 500, null)
                .memoryCacheSize(4 * 1024 * 1024)
                .memoryCache(new LruMemoryCache(4 * 1024 * 1024))
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .defaultDisplayImageOptions(defaultOptions)
                .build();

        ImageLoader.getInstance().init(config);
    }
}
