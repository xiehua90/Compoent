package com.example.thinkdo.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.thinkdo.compoentdemo.R;

import butterknife.BindView;

public class ConstraintFragment extends BaseFragment implements View.OnClickListener {
    EditText et;
    Button btn;

    @BindView(R.id.textView4)
    TextView textView4;

    @Override
    public int getLayoutId() {
        return R.layout.constraint_test;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        et = view.findViewById(R.id.editText);
        btn = view.findViewById(R.id.button);

//        et.setFocusable(false);
//        et.setFocusableInTouchMode(false);

        et.setOnClickListener(this);
        btn.setOnClickListener(this);

        test();
    }



    private void test(){

//
//        StringBuilder builder = new StringBuilder();
//        for (int i = 127; i < 200; i++) {
//            byte b = (byte)i;
//
//            byte[] bytes = new byte[2];
//            bytes[0] = b;
//            bytes[1] = 0x2c;
//            String str = new String(bytes, new Char);
//            builder.append(i+":"+str+"\n");
//        }
//
//        textView4.setText(builder.toString());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.editText:
                Log.d("TAG","editText");
                break;
            case R.id.button:
                Log.d("TAG","button");
                break;
        }
    }
}
