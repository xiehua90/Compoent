package com.example.thinkdo.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.EditText;

public class PopEditText extends EditText {
    public PopEditText(Context context) {
        super(context);
        init();
    }

    public PopEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public PopEditText(final Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
//        setLongClickable(false);
//        setCustomSelectionActionModeCallback(new ActionMode.Callback() {
//            @Override
//            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//                return false;
//            }
//
//            @Override
//            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//                return false;
//            }
//
//            @Override
//            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//                return false;
//            }
//
//            @Override
//            public void onDestroyActionMode(ActionMode mode) {
//
//            }
//        });
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            setCustomInsertionActionModeCallback(new ActionMode.Callback() {
//                @Override
//                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//                    return false;
//                }
//
//                @Override
//                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//                    return false;
//                }
//
//                @Override
//                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//                    return false;
//                }
//
//                @Override
//                public void onDestroyActionMode(ActionMode mode) {
//
//                }
//            });
//        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
