package com.example.xh.kotlin.fragment

import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.example.xh.kotlin.BaseFragement
import com.example.xh.kotlin.R
import kotlinx.android.synthetic.main.fragment_widget.*
import java.util.*

// https://github.com/xiehua90/Android-PickerView
class WidgetFragment : BaseFragement(), DatePicker.OnDateChangedListener, TimePicker.OnTimeChangedListener, OnTimeSelectListener {
    override fun onTimeSelect(date: Date?, v: View?) {
    }

    override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val startDate = Calendar.getInstance()
        val endDate = Calendar.getInstance()
        endDate.roll(Calendar.MONTH,6)


        val timePicker = TimePickerBuilder(activity, this)
                .setType(booleanArrayOf(true, true, true, true, true, false))
                .isDialog(true)
                .setContentTextSize(20)
                .setRangDate(startDate,endDate)
                .build()
        timePicker.show()
    }

    override fun onDateChanged(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_widget
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPicker()
    }

    fun initPicker() {
        val calendar = Calendar.getInstance()
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), this)

        timePicker.setIs24HourView(true)
        timePicker.setOnTimeChangedListener(this)
    }



}