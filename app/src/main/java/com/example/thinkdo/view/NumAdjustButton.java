package com.example.thinkdo.view;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.thinkdo.compoentdemo.R;

/**
 * Created by xh on 2018/3/23.
 */

public class NumAdjustButton extends LinearLayout implements View.OnClickListener, TextWatcher {
    OnNumChangedListener callback;
    ImageButton btnPlus, btnReduce;

    EditText editText;
    int maxNum;
    int curNum = 0;

    public NumAdjustButton(Context context) {
        this(context, null);
    }

    public NumAdjustButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public NumAdjustButton(final Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.NumAdjustButton);
        maxNum = array.getInteger(R.styleable.NumAdjustButton_maxNum, 10);
        array.recycle();

        LayoutInflater.from(context).inflate(R.layout.view_num_adjust, this);
        btnPlus = (ImageButton) findViewById(R.id.btn_plus);
        btnReduce = (ImageButton) findViewById(R.id.btn_reduce);
        editText = (EditText) findViewById(R.id.editText);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == KeyEvent.ACTION_DOWN){
                    editText.clearFocus();
                }
                return false;
            }
        });

        btnPlus.setOnClickListener(this);
        btnReduce.setOnClickListener(this);
        editText.addTextChangedListener(this);
        editText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_plus:
                if (callback != null) {
                    if (curNum < maxNum) {
                        callback.onNumChanged(curNum + 1);
                    } else {
                        callback.onNumOutofBoundary(1);
                    }
                }

                curNum = curNum + 1 > maxNum ? maxNum : curNum + 1;
                editText.setText(String.valueOf(curNum));
                editText.setCursorVisible(false);
                break;
            case R.id.btn_reduce:
                if (callback != null) {
                    if (curNum > 0) {
                        callback.onNumChanged(curNum - 1);
                    } else {
                        callback.onNumOutofBoundary(-1);
                    }
                }
                curNum = curNum <= 0 ? 0 : curNum - 1;
                editText.setText(String.valueOf(curNum));
                editText.setCursorVisible(false);
                break;
            case R.id.editText:
                editText.setCursorVisible(true);
                break;
        }
    }


    public int getNum() {
        return curNum;
    }

    public void setNum(int curNum) {
        this.curNum = curNum;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        int i;
        try {
            i = Integer.parseInt(s.toString());
        } catch (NumberFormatException e) {
            i = 0;
        }

        if (i < 0){
            if (callback!=null)callback.onNumOutofBoundary(-1);
            editText.setText(String.valueOf(curNum));
        }
        else if(i > maxNum){
            if (callback!=null)callback.onNumOutofBoundary(1);
            editText.setText(String.valueOf(curNum));
        }
        else{
            if (i != curNum) {
                if (callback != null) {
                    callback.onNumChanged(i);
                }
                curNum = i;
            }
        }
        editText.setSelection(editText.getText().length());
    }

    public void setOnNumChangedListener(OnNumChangedListener listener) {
        this.callback = listener;
    }


    public interface OnNumChangedListener {
        void onNumChanged(int num);

        void onNumOutofBoundary(int num);

    }
}
