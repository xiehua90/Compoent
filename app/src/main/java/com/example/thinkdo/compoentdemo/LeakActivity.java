package com.example.thinkdo.compoentdemo;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;

public class LeakActivity extends AppCompatActivity {

    private static class MyHandler extends Handler {
        private final WeakReference<Context> mContext;

        public MyHandler(Context context) {
            mContext = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            Context context = mContext.get();
            if (context != null) {
                switch (msg.what) {
                    case 1:
                        context.startActivity(new Intent(context, LeakActivity.class));
                        break;
                }
            }
        }
    }

    Handler handler = new MyHandler(this);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_leak);

        Log.d("TAG", "LeakActivity onCreate()");


        findViewById(R.id.btn1).setOnClickListener(view -> {
            handler.removeMessages(1);
            startActivity(new Intent(this, LeakActivity.class));
        });

        findViewById(R.id.btn2).setOnClickListener(view -> {
            handler.removeMessages(1);
            handler.sendEmptyMessageDelayed(0, 1000 * 60 * 10);
        });


        handler.sendEmptyMessageDelayed(1, 500);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("TAG", "LeakActivity onDestroy()");
    }
}
