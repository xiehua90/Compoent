package com.example.xh.kotlin.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.bigkoo.pickerview.adapter.NumericWheelAdapter;
import com.bigkoo.pickerview.configure.PickerOptions;
import com.contrarywind.view.WheelView;
import com.example.xh.kotlin.R;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class WheelPopupWindow extends PopupWindow {

    Context mContext;

    public WheelPopupWindow(Context context) {
//        super(context);
        this.mContext = context;

        init();
    }

    private void init() {
        View pickContainerView = LayoutInflater.from(mContext).inflate(R.layout.popupwindow_wheeltime_appoint, null, false);
//        WheelView minuteView = pickContainerView.findViewById(R.id.min2);
        PickerOptions options = new PickerOptions(PickerOptions.TYPE_PICKER_TIME);
//        Calendar startCalendar = getStartCalendar();
//
//        Calendar calendar1 = Calendar.getInstance();
//        calendar1.setTimeInMillis(startCalendar.getTimeInMillis());
//        calendar1.set(Calendar.MINUTE, 0);

//        options.startDate = calendar1;
        options.startDate = Calendar.getInstance();
        options.type = new boolean[]{true, true, true, true, true, false};
        XWheelTime wheelTime = new XWheelTime(pickContainerView, options);

        wheelTime.setSelectChangeCallback(() -> {

        });

//        List<Integer> minuteData = Arrays.asList(0, 30);
//        minuteView.setAdapter(new ArrayWheelAdapter());

        setContentView(pickContainerView);
        setWidth(800);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
    }

//    private Calendar getStartCalendar() {
//        int step = 30 * 60 * 1000;
//
//        Calendar calendar = Calendar.getInstance();
//        long now = calendar.getTimeInMillis();
//
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//        long temp = calendar.getTimeInMillis();
//
//        for (int i = 1; i < 100; i++) {
//            temp += step;
//
//            if (temp > now) {
//                Calendar result = Calendar.getInstance();
//                result.setTimeInMillis(temp);
//                return result;
//            }
//        }
//        return null;
//    }
}
