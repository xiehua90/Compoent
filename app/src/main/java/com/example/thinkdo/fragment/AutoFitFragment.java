package com.example.thinkdo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.thinkdo.compoentdemo.R;

import me.grantland.widget.AutofitTextView;



/**
 * Created by xh on 2018/4/12.
 */

public class AutoFitFragment extends Fragment implements TextWatcher {
    EditText et;
    AutofitTextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_auto_fit_text, null);

        et = (EditText) view.findViewById(com.example.thinkdo.compoentdemo.R.id.editText);
        textView = (AutofitTextView) view.findViewById(R.id.autofitTextView);

        et.addTextChangedListener(this);
        showSoftInput();

        return view;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        textView.setText(s.toString());
    }

    public void showSoftInput(){
        et.setFocusable(true);
        et.setFocusableInTouchMode(true);
        et.requestFocus();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

    }

}
