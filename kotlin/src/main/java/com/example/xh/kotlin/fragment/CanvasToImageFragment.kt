package com.example.xh.kotlin.fragment

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.text.TextUtils
import android.view.View
import com.example.xh.kotlin.BaseFragement
import com.example.xh.kotlin.R
import kotlinx.android.synthetic.main.fragment_canvas.*

class CanvasToImageFragment : BaseFragement() {
    override fun getLayoutResId(): Int {
        return R.layout.fragment_canvas
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editText.setText("你好, 世界!")
        btn_convert.setOnClickListener {
            imageView.setImageBitmap(canvasToImage(editText.text.toString()))
        }
    }

    val spaceMult = 1.1f
    val spaceAdd = 0f
    val fontSize = 24f

    val width = 384
    var height = 600
    val mid = 200f
    val padding = 10f
    val valueWidth = (width - mid - 2 * padding).toInt()

    val paint = TextPaint().apply {
        color = Color.BLACK
        textSize = fontSize
        isAntiAlias = true
        style = Paint.Style.FILL
        isFakeBoldText = false
    }

    fun canvasToImage(txt: String): Bitmap {
        val txtRange = listOf(0, 0, 0)

        height = 0
        val gapY = mutableListOf<Int>()
        gapY.add(0)
        for (i in txtRange) {
            gapY.add(gapY.last() + measureHeight(txt, txt) + 2 * padding.toInt())
        }
        height = gapY.last()

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.setBitmap(bitmap)
        drawTable(canvas = canvas, drawLinesY = gapY.drop(1).dropLast(1))

        for ((i, v) in txtRange.withIndex()) {
            canvas.save()
            canvas.translate(0f, gapY[i].toFloat())
            drawTxt(txt, txt, canvas)
            canvas.restore()
        }

        return bitmap
    }

    fun drawTxt(leftTxt: String, rightTxt: String, canvas: Canvas) {
        val leftLayout = StaticLayout(leftTxt, 0, leftTxt.length, paint, (mid - 2 * padding).toInt(), Layout.Alignment.ALIGN_NORMAL, spaceMult, spaceAdd, true, TextUtils.TruncateAt.END, (mid - 2 * padding).toInt())

        canvas.save()
        canvas.translate(padding, padding)
        leftLayout.draw(canvas)
        canvas.restore()

        val rightLayout = StaticLayout(rightTxt, 0, rightTxt.length, paint, valueWidth, Layout.Alignment.ALIGN_NORMAL, spaceMult, spaceAdd, true, TextUtils.TruncateAt.END, valueWidth)

        canvas.save()
        canvas.translate(mid + padding, padding)
        rightLayout.draw(canvas)
        canvas.restore()
    }

    fun measureHeight(leftTxt: String, rightTxt: String): Int {
        var layout = StaticLayout(leftTxt, 0, leftTxt.length, paint, (mid - 2 * padding).toInt(), Layout.Alignment.ALIGN_NORMAL, spaceMult, spaceAdd, true, TextUtils.TruncateAt.END, (mid - 2 * padding).toInt())

        val height = layout.height
        layout = StaticLayout(rightTxt, 0, rightTxt.length, paint, valueWidth, Layout.Alignment.ALIGN_NORMAL, spaceMult, spaceAdd, true, TextUtils.TruncateAt.END, valueWidth)
        return if (layout.height > height) layout.height else height
    }

    fun drawTable(canvas: Canvas, drawLinesY: List<Int>) {
        val left = 0f
        val right = width.toFloat()
        val top = 0f
        val bottom = height.toFloat()

        canvas.drawLine(left, top + paint.strokeWidth / 2, right, top + paint.strokeWidth / 2, paint)
        canvas.drawLine(left, bottom - paint.strokeWidth / 2, right, bottom - paint.strokeWidth / 2, paint)
        canvas.drawLine(left + paint.strokeWidth / 2, top, left + paint.strokeWidth / 2, bottom, paint)
        canvas.drawLine(right - paint.strokeWidth / 2, top, right - paint.strokeWidth / 2, bottom, paint)
        canvas.drawLine(mid, top, mid, bottom, paint)

        //drawGapLine
        for (gapY in drawLinesY) {
            canvas.drawLine(left, gapY.toFloat(), right, gapY.toFloat(), paint)
        }
    }


}