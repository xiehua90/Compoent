package com.example.thinkdo.view;

import android.content.Context;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.example.thinkdo.model.CollapseBean;

/**
 * Created by xh on 2018/4/15.
 */

public class CollapseTextView extends TextView {
    private int mContentWidth;

    public int collapseState = CollapseBean.CollapseState.Init;
    private int lines = 3;
    private int maxLines = Integer.MAX_VALUE;
    CollapseStateChange callback;

    public CollapseTextView(Context context) {
        super(context);
    }

    public CollapseTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CollapseTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        mContentWidth = widthSize - getPaddingStart() - getPaddingEnd();
        log("onMeasure state=" + collapseState + "");

        if (collapseState == CollapseBean.CollapseState.Init) {
            setCollapseState(isTrunation() ? CollapseBean.CollapseState.More : CollapseBean.CollapseState.Less);
            if (callback != null) callback.onStateChange(getCollapseState());
            if (getMaxLines() != lines) {
                setMaxLines(lines);
            }
        }
    }


    private boolean isTrunation() {
        Paint paint = getPaint();
        float length = paint.measureText(getText().toString());
        return length > lines * mContentWidth;
    }

    public int getCollapseState() {
        return collapseState;
    }

    public void setCollapseState(int collapseState) {
        this.collapseState = collapseState;

        if (collapseState == CollapseBean.CollapseState.Collapse) {
            if (getMaxLines() != maxLines)
                setMaxLines(maxLines);
        } else {
            if (getMaxLines() != lines)
                setMaxLines(lines);
        }
    }

    private void log(String str) {
        Log.d("TAG", str);
    }

    public void setCollapseStateChange(CollapseStateChange callback) {
        this.callback = callback;
    }

    public interface CollapseStateChange {
        void onStateChange(int state);
    }
}
