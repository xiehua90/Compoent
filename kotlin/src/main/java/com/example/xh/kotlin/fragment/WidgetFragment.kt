package com.example.xh.kotlin.fragment

import android.app.IntentService
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.text.*
import android.view.View
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.example.xh.kotlin.BaseFragement
import com.example.xh.kotlin.R
import kotlinx.android.synthetic.main.fragment_widget.*
import java.util.*
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.URLSpan
import android.text.util.Linkify
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.configure.PickerOptions
import com.bumptech.glide.Glide
import com.example.xh.kotlin.widget.WheelDialog
import com.example.xh.kotlin.widget.WheelPopupWindow
import com.example.xh.kotlin.widget.XWheelTime
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_widget.textView2
import kotlinx.android.synthetic.main.spinnner.*
import java.text.SimpleDateFormat


// https://github.com/xiehua90/Android-PickerView
class WidgetFragment : BaseFragement(), DatePicker.OnDateChangedListener, TimePicker.OnTimeChangedListener, OnTimeSelectListener, View.OnClickListener {


    override fun onClick(v: View?) {
        val frameLayout = FrameLayout(activity)
        frameLayout.setBackgroundColor(Color.GRAY)
        val popupWindow = PopupWindow(frameLayout, 700, 400)
        popupWindow.isFocusable = true
        popupWindow.isOutsideTouchable = true


        when (v) {
            btn1 -> {
                val startDate = Calendar.getInstance()
                val endDate = Calendar.getInstance()
                endDate.roll(Calendar.DAY_OF_MONTH, 15)
                endDate.roll(Calendar.MONTH, 3)

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
            btn4 -> {
//                val startDate = Calendar.getInstance()
//                val endDate = Calendar.getInstance()
//                endDate.set(2020, 8, 9, 6, 12, 0)
//
//                val pickContainerView = LayoutInflater.from(context).inflate(com.bigkoo.pickerview.R.layout.pickerview_time, view as ViewGroup, false)
//
//                val options = PickerOptions(PickerOptions.TYPE_PICKER_TIME)
//                pickContainerView.setBackgroundColor(Color.GRAY)
//                options.startDate = startDate
//                options.endDate = endDate
//                options.type = booleanArrayOf(true, true, true, true, true, true)
//                val wheelTime = XWheelTime(pickContainerView, options)
//
//                val pop = PopupWindow(pickContainerView, 800, ViewGroup.LayoutParams.WRAP_CONTENT)
//                pop.showAsDropDown(btn4)
//
//                pickContainerView.findViewById<View>(R.id.btnSubmit).setOnClickListener {
//                    Snackbar.make(view as ViewGroup, wheelTime.time, Snackbar.LENGTH_SHORT).show()
//                    pop.dismiss()
//                }


                val wheelpopupWindow = WheelPopupWindow(activity!!)
                wheelpopupWindow.isOutsideTouchable = true
                wheelpopupWindow.showAsDropDown(btn4)


                return

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
        btn4.setOnClickListener(this)

        editText.filters = arrayOf(InputFilter { source, start, end, dest, dstart, dend ->

            val tem = StringBuffer(dest).replace(dstart, dstart, source.toString())

            if (source != null) {
                if (source.isNotEmpty()){

                    if (source is Spanned){
                        val temp = SpannableString(source.toString().toUpperCase())
                        TextUtils.copySpansFrom(source, start, end,null, temp, 0)
                        return@InputFilter temp
                    }

                    return@InputFilter source.toString().toUpperCase()
                }
            }
            null
        }, InputFilter.LengthFilter(10))


        btn_wechat.setOnClickListener {
            val list = activity!!.packageManager.getInstalledPackages(0)

//            for (item in list) {
//                Log.d("TAG", item.applicationInfo.packageName)
//            }

            val existFlag = activity!!.packageManager.getPackageInfo("com.example.xh.kotlin", 0)
            Log.d("TAG", "existFlag:$existFlag")

        }

        Glide.with(activity!!)
                .load("https://image.freepik.com/free-photo/hair-style-street-fashion-beautiful-girl_1139-844.jpg")
                .centerCrop()
                .into(photo_view)
    }

    fun initPicker() {
        val calendar = Calendar.getInstance()
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), this)

        timePicker.setIs24HourView(true)
        timePicker.setOnTimeChangedListener(this)

        val htmlText = "<a href=\"https://zhidao.baidu.com/question/343704535.html\">https://zhidao.baidu.com/question/343704535.html</a>\n"
//        RichText.from(htmlText).into(textView2)
        textView2.autoLinkMask = Linkify.WEB_URLS
//        textView2.text = Html.fromHtml(htmlText)

        textView2.text = getSpanninfo()
    }


    fun getSpanninfo(): SpannableString {
        val spannableString = SpannableString("设置文字的背景色为淡绿色\n" +
                "无论何时,何地,你只要的说一声,我都会出现在你的身边!")
        val colorSpan = BackgroundColorSpan(Color.GRAY)
        val sizeSpan04 = RelativeSizeSpan(1.8f)
        val sizeSpan05 = RelativeSizeSpan(1.6f)
        val foregroundColorSpan = ForegroundColorSpan(Color.RED)
        val urlSpan = URLSpan("https://www.baidu.com")

        spannableString.setSpan(colorSpan, 1, 8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(sizeSpan04, 5, 9, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(sizeSpan05, 15, 20, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(foregroundColorSpan, 1, 10, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(urlSpan, 20, 28, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)


        val array = spannableString.getSpans(0, spannableString.length, BackgroundColorSpan::class.java)


        return spannableString
    }


    fun getHtmlText(text: String): String {
        var temp = text.replace("\n", "<br/>")
        temp = temp.replace(" ", "&nbsp;")

        temp = "<div style=\"color:#000;font-size:20px;\">$temp</div>"
        return temp
    }


    fun xWheelTimeinit(): View {
        val startDate = Calendar.getInstance()
        val endDate = Calendar.getInstance()
        endDate.set(2020, 8, 9, 6, 12, 0)

        val pickContainerView = LayoutInflater.from(context).inflate(com.bigkoo.pickerview.R.layout.pickerview_time, view as ViewGroup, false)
        val view = pickContainerView.findViewById<LinearLayout>(R.id.timepicker);

        val options = PickerOptions(PickerOptions.TYPE_PICKER_TIME)
        pickContainerView.setBackgroundColor(Color.GRAY)

        options.startDate = startDate
        options.endDate = endDate
        options.type = booleanArrayOf(true, true, true, true, true, true)
        XWheelTime(pickContainerView, options)

        return pickContainerView
    }


}