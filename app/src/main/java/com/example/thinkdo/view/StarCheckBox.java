package com.example.thinkdo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;

import com.example.thinkdo.compoentdemo.R;


/**
 * Created by Administrator on 2016/7/5.
 */
public class StarCheckBox extends CheckBox {
    public StarCheckBox(Context context) {
        this(context, null);
    }

    public StarCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        setButtonDrawable(R.drawable.selector_checkbox);
    }

}
