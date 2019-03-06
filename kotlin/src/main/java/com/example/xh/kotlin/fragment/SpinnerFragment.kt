package com.example.xh.kotlin.fragment

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.xh.kotlin.R
import kotlinx.android.synthetic.main.item_recycler_main.*
import kotlinx.android.synthetic.main.spinnner.*

class SpinnerFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.spinnner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinner: Spinner = view.findViewById(R.id.spinner)
        val array = activity?.resources?.getStringArray(R.array.city_array)

        spinner.adapter = ArrayAdapter(activity, android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, array)

        textView1.setTextColor(Color.WHITE)
        textView2.setTextColor(Color.WHITE)
        setBackground(textView1)
        setBackground2(textView2)

        textView1.text = "${resources.configuration.screenWidthDp}dp * ${resources.configuration.screenHeightDp}dp"
    }


    fun setBackground(view: View){
        val drawable = GradientDrawable()

        drawable.setColor(Color.RED)
        drawable.setStroke(1, Color.GREEN)

        val cornerRadius = resources.displayMetrics.density * 8

//        drawable.cornerRadius = cornerRadius
        drawable.cornerRadii = floatArrayOf(cornerRadius, cornerRadius, cornerRadius, cornerRadius,
                cornerRadius, cornerRadius, cornerRadius, cornerRadius)

        view.background = drawable
    }

    fun setBackground2(view: View){
        val drawable = GradientDrawable()

        drawable.setColor(Color.RED)
        drawable.setStroke(1, Color.GREEN)

        val cornerRadius = resources.displayMetrics.density * 8

        drawable.cornerRadius = cornerRadius
//        drawable.cornerRadii = floatArrayOf(cornerRadius, cornerRadius, cornerRadius, cornerRadius,
//                cornerRadius, cornerRadius, cornerRadius, cornerRadius)

        view.background = drawable
    }


}