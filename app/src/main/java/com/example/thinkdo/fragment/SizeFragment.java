package com.example.thinkdo.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thinkdo.compoentdemo.R;

/**
 * Created by Administrator on 2016/4/28.
 */
public class SizeFragment extends Fragment {
    private SeekBar seekBar;
    private TextView textView;
    private LinearLayout container;
    private float density;
    private int critical;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        density = getResources().getDisplayMetrics().density;
        critical = (int) (80 * density);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_size, container, false);
        initView(view);
        return view;
    }


    private void initView(View view) {
        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        textView = (TextView) view.findViewById(R.id.textView_progressShow);
        container = (LinearLayout) view.findViewById(R.id.container);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText(String.valueOf(progress));
                sendToast("hello world");
                setContainerHeight((int) (density * progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void sendToast(String msg) {
        Toast toast = new Toast(getActivity());
        LinearLayout linearLayout = (LinearLayout) LinearLayout.inflate(getActivity(), R.layout.view_toast, null);

        TextView tv = (TextView) linearLayout.findViewById(R.id.textView);
        tv.setText(msg);

        toast.setView(linearLayout);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.show();
    }

    private void setContainerHeight(int height) {
        if (height < 0) height = 0;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) container.getLayoutParams();
        lp.height = height;

        int gravity = height > critical ? Gravity.TOP : Gravity.BOTTOM;
        container.setGravity(gravity);
        container.setLayoutParams(lp);
    }
}
