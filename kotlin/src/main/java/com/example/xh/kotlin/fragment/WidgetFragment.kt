package com.example.xh.kotlin.fragment

import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.view.View
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.example.xh.kotlin.BaseFragement
import com.example.xh.kotlin.R
import kotlinx.android.synthetic.main.fragment_widget.*
import java.util.*
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.text.style.RelativeSizeSpan
import android.util.Log
import android.view.ViewGroup
import android.widget.*
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.google.android.material.snackbar.Snackbar
import java.lang.reflect.Array
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList


// https://github.com/xiehua90/Android-PickerView
class WidgetFragment : BaseFragement(), DatePicker.OnDateChangedListener, TimePicker.OnTimeChangedListener, OnTimeSelectListener, View.OnClickListener {


    override fun onClick(v: View?) {
        val frameLayout = FrameLayout(activity)
        frameLayout.setBackgroundColor(Color.GRAY)
        val popupWindow = PopupWindow(frameLayout, 500, 400)
        popupWindow.isFocusable = true
        popupWindow.isOutsideTouchable = true


        when (v) {
            btn1 -> {
                val startDate = Calendar.getInstance()
                val endDate = Calendar.getInstance()
                endDate.roll(Calendar.MONTH, 6)

                val timePicker = TimePickerBuilder(activity)
                { date, v ->
                    Snackbar.make(view!!, getTime(date), Snackbar.LENGTH_INDEFINITE).show()
                    popupWindow.dismiss()
                }
                        .setType(booleanArrayOf(true, true, true, true, false, false))
                        .setLabel("年", "月", "日", "时", "分", "")
                        .setCancelText("取消")
                        .setSubmitText("确定")
                        .setRangDate(startDate, endDate)
                        .setDecorView(frameLayout)
                        .build()
                timePicker.setOnDismissListener { ob -> popupWindow.dismiss() }
                timePicker.show(false)

            }
            btn2 -> {
                val startDate = Calendar.getInstance()
                val endDate = Calendar.getInstance()
                endDate.roll(Calendar.MONTH, 6)

                val timePicker = TimePickerBuilder(activity)
                { date, v ->
                    Snackbar.make(view!!, getTime(date), Snackbar.LENGTH_INDEFINITE).show()
                    popupWindow.dismiss()
                }
                        .setType(booleanArrayOf(true, true, true, false, false, false))
                        .setLabel("年", "月", "日", "时", "分", "")
                        .setCancelText("取消")
                        .setSubmitText("确定")
                        .setRangDate(startDate, endDate)
                        .setDecorView(frameLayout)
                        .build()
                timePicker.setOnDismissListener { ob -> popupWindow.dismiss() }
                timePicker.show(false)
            }

            btn3 -> {
                var hourList = mutableListOf<Int>()
                for (i in 1..30) {
                    hourList.add(i)
                }
                var minList = mutableListOf<Int>()
                for (i in 0 until 60) {
                    minList.add(i)
                }
                val optionPick = OptionsPickerBuilder(activity) { options1, options2, options3, v ->
                    Snackbar.make(view!!, "${hourList[options1]}时 ${minList[options2]}分", Snackbar.LENGTH_INDEFINITE).show()
                    popupWindow.dismiss()
                }
                        .setCancelText("取消")
                        .setSubmitText("确定")
                        .setLabels("时", "分", "")
                        .setDecorView(frameLayout)
                        .build<Int>()


                optionPick.setNPicker(hourList, minList, null)
                optionPick.setOnDismissListener { ob -> popupWindow.dismiss() }
                optionPick.show(false)

            }
        }

        popupWindow.showAsDropDown(v!!)
    }

    private fun getTime(date: Date): String {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.time)
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return format.format(date)
    }


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

        btn.setBackgroundResource(R.drawable.selector_btn_bg)
        btn1.setOnClickListener(this)
        btn2.setOnClickListener(this)
        btn3.setOnClickListener(this)
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

        temp = "<div style=\"color:#000;font-size:20px;\">$temp</div>"
        return temp
    }


}