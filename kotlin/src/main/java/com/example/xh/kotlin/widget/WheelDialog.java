package com.example.xh.kotlin.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.bigkoo.pickerview.configure.PickerOptions;
import com.example.xh.kotlin.R;

import java.util.Calendar;

import androidx.annotation.NonNull;

public class WheelDialog extends Dialog {
    public WheelDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        endDate.set(2020, 8, 9, 6, 12, 0);

        View pickContainerView = LayoutInflater.from(getContext()).inflate(com.bigkoo.pickerview.R.layout.pickerview_time, null, false);

        PickerOptions options = new PickerOptions(PickerOptions.TYPE_PICKER_TIME);

        options.startDate = startDate;
        options.endDate = endDate;
        options.type = new boolean[]{true, true, true, true, true, true};
        new XWheelTime(pickContainerView, options);


        setContentView(pickContainerView);
    }


}
