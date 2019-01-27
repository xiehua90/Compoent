package com.example.thinkdo.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.thinkdo.compoentdemo.R;

/**
 * Created by xh on 2018/4/3.
 */

public class DialogFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_dialog, container, false);
        Button btn = (Button) view.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressBar progressBar = new ProgressBar(getActivity(), null, android.R.attr.progressBarStyleLargeInverse);
                ProgressBar progressBar1 = new ProgressBar(getActivity(), null, android.R.attr.progressBarStyleLargeInverse);


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("Title");
//                builder.setMessage("content");
//                builder.setCustomTitle(progressBar);
                builder.setCancelable(true);
                builder.setView(progressBar1);
//                builder.setNegativeButton("negative", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//                builder.setPositiveButton("positiveButton", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
                builder.show();
            }
        });

        return view;
    }
}
