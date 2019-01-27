package com.example.thinkdo.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thinkdo.compoentdemo.R;

/**
 * Created by Administrator on 2016/5/6.
 */
public class ToastFragment extends Fragment {
    private TextView mView;
    private Handler handler = new Handler();
    private int Index = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = new TextView(getActivity());
        mView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        mView.setTextColor(Color.RED);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        handler.postAtTime(new Runnable() {
//            @Override
//            public void run() {
//                sendToast("Hello World!" + Index++);
//                handler.postAtTime(this, 10000);
//            }
//        }, 10000);


        View view = inflater.inflate(R.layout.fragment_window_params, container, false);
        TextView tv = (TextView) view.findViewById(R.id.textView);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.postAtTime(new Runnable() {
                    @Override
                    public void run() {
                        sendToast("Hello World! " + Index++);
                    }
                }, 1000);

            }
        });
        return view;
    }


    private void sendToast(String msg) {
//        Toast toast = new Toast(getActivity());
//        toast.setGravity(Gravity.BOTTOM, 0, 0);
//        mView.setText(msg);
//        toast.setView(mView);
//        toast.setDuration(Toast.LENGTH_LONG);
//        toast.show();

        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("Error")
                .setMessage(msg)
                .create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();

    }


}
