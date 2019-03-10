package com.example.xh.kotlin.fragment

import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.example.xh.kotlin.BaseFragement
import com.example.xh.kotlin.R
import kotlinx.android.synthetic.main.fragment_widget.*
import java.util.*
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.text.style.RelativeSizeSpan


// https://github.com/xiehua90/Android-PickerView
class WidgetFragment : BaseFragement(), DatePicker.OnDateChangedListener, TimePicker.OnTimeChangedListener, OnTimeSelectListener {
    override fun onTimeSelect(date: Date?, v: View?) {
    }

    override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val startDate = Calendar.getInstance()
        val endDate = Calendar.getInstance()
        endDate.roll(Calendar.MONTH, 6)


        val timePicker = TimePickerBuilder(activity, this)
                .setType(booleanArrayOf(true, true, true, true, true, false))
                .isDialog(true)
                .setContentTextSize(20)
                .setRangDate(startDate, endDate)
                .build()
        timePicker.show()

        getSpanninfo()

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

//        val htmlText = "<h3>Test1</h3><p>hello</p>"
//        RichText.from(htmlText).into(textView2)
        textView2.text = Html.fromHtml(getHtmlText("你们好\n   我很好\nbyte"))
    }


    fun getSpanninfo(): SpannableString {
        val spannableString = SpannableString("设置文字的背景色为淡绿色")
        val colorSpan = BackgroundColorSpan(Color.parseColor("#AC00FF30"))
        val sizeSpan04 = RelativeSizeSpan(1.8f)
        val sizeSpan05 = RelativeSizeSpan(1.6f)

        spannableString.setSpan(colorSpan, 1, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(sizeSpan04, 5, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(sizeSpan05, 6, 8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)


        val array = spannableString.getSpans(0, spannableString.length, BackgroundColorSpan::class.java)


        return spannableString
    }


    fun getHtmlText(text: String): String {
        var temp = text.replace("\n", "<br/>")
        temp = temp.replace(" ", "&nbsp;")

        temp= "<div style=\"color:#000;font-size:20px;\">$temp</div>"
        return temp
    }


}