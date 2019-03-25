package com.example.xh.kotlin.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class VView extends LinearLayout {
    public VView(Context context) {
        super(context);
    }

    public VView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public VView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setChildWidth(widthMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void setChildWidth(int widthMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);

            ViewGroup.LayoutParams lp = child.getLayoutParams();
            lp.width = width / 2;
            lp.height = 400;
        }
    }
}
