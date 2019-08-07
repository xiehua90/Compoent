package com.example.xh.kotlin.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.example.xh.kotlin.BaseFragement
import com.example.xh.kotlin.R
import kotlinx.android.synthetic.main.fragment_richeditor.*

class RicheditorFragment : BaseFragement() {
    override fun getLayoutResId(): Int {
        return R.layout.fragment_richeditor
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val htmlText = "<h4>Test1，<br>&nbsp;&nbsp;我最<div style=\"color:#ff0000;font-size:60px;\">帅</h4> 哈哈</div>\n" +
                "<div style=\"color:#ff0000;font-size:20px;\">对比</div>\n" +
                "<div style=\"color:#ff0000;font-size:20px;\"> 你们<h1>好 <br/>&nbsp;&nbsp;&nbsp;我很好<br/> byte</div>我就是要插队</h1>\n" +
                "<img src='https://goss3.vcg.com/editorial/vcg/800/new/VCG111199039098.jpg' />\n" +
                "<a href=\"https://zhidao.baidu.com/question/343704535.html\">https://zhidao.baidu.com/question/343704535.html</a>"

//        richEditor.html = htmlText
        richEditor.setEditorFontSize(30)
        richEditor.setEditorFontColor(Color.RED)

        richEditor.setOnTextChangeListener { text -> textView.text = text }
    }
}