package com.example.thinkdo.fragment;


import android.content.Intent;
import android.view.View;

import com.example.thinkdo.compoentdemo.R;
import com.google.zxing.client.android.CaptureActivity;

import butterknife.OnClick;

/**
 * Created by xh on 2018/5/2.
 */

public class QRCodeFragment extends BaseFragment {
    @Override
    public int getLayoutId() {
        return R.layout.frag_qr_code;
    }


    @OnClick({R.id.btn})
    public void onClick(View view) {
        startActivity(new Intent(getActivity(), CaptureActivity.class));
    }

}
