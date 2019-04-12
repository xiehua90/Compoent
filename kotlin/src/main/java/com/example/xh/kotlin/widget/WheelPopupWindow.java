package com.example.xh.kotlin.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.bigkoo.pickerview.configure.PickerOptions;
import com.example.xh.kotlin.R;

import java.util.Calendar;

public class WheelPopupWindow extends PopupWindow {

    Context mContext;

    public WheelPopupWindow(Context context) {
//        super(context);
        this.mContext = context;

        init();
    }

    private void init() {
        View pickContainerView = LayoutInflater.from(mContext).inflate(R.layout.popupwindow_wheeltime_appoint, null, false);

        PickerOptions options = new PickerOptions(PickerOptions.TYPE_PICKER_TIME);

        options.startDate = getStartCalendar();
        options.type = new boolean[]{true, true, true, true, true, false};
        XWheelTime wheelTime = new XWheelTime(pickContainerView, options);


        setContentView(pickContainerView);
        setWidth(800);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private Calendar getStartCalendar() {
        int step = 30 * 60 * 1000;

        Calendar calendar = Calendar.getInstance();
        long now = calendar.getTimeInMillis();

        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long temp = calendar.getTimeInMillis();

        for (int i = 1; i < 100; i++) {
            temp += step;

            if (temp > now) {
                Calendar result = Calendar.getInstance();
                result.setTimeInMillis(temp);
                return result;
            }
        }
        return null;
    }
}
