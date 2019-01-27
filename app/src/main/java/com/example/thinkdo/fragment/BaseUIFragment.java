package com.example.thinkdo.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.thinkdo.compoentdemo.R;

public class BaseUIFragment extends Fragment {

    EditText et;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_ui, container, false);

        view.findViewById(R.id.button).setOnClickListener(v -> {
            InputMethodManager inputService = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputService.toggleSoftInput(0, 0);
        });

        et = view.findViewById(R.id.editText);
        et.setOnTouchListener((View v, MotionEvent event) -> true);


        return view;
    }
}
