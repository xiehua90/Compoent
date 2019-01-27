package com.example.thinkdo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.thinkdo.compoentdemo.R;
import com.example.thinkdo.view.NumAdjustButton;

/**
 * Created by xh on 2018/3/23.
 */

public class AdjustNumBtnFragment extends Fragment {
    NumAdjustButton button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_adjsut_num, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button = (NumAdjustButton) view.findViewById(R.id.numAdjust);

        button.setOnNumChangedListener(new NumAdjustButton.OnNumChangedListener() {
            @Override
            public void onNumChanged(int num) {
                Toast.makeText(getActivity(), String.valueOf(num), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNumOutofBoundary(int num) {
                Toast.makeText(getActivity(), "OutofBoundary", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
