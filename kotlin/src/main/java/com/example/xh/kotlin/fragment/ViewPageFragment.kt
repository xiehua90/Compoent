package com.example.xh.kotlin.fragment


import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.xh.kotlin.BaseFragement
import com.example.xh.kotlin.R
import kotlinx.android.synthetic.main.fragment_view_page.*
import kotlinx.android.synthetic.main.fragment_view_page.view.*

class ViewPageFragment: BaseFragement() {
    override fun getLayoutResId(): Int {
        return R.layout.fragment_view_page
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view_pager.adapter = MyAdapter(activity!!)

    }


    class MyAdapter(val context: Context): PagerAdapter(){


        override fun getCount(): Int {
            return 10
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val textView = TextView(context)
            textView.setText("position:$position")
            textView.gravity = Gravity.CENTER
            textView.tag = "textView$position"
            container.addView(textView)
            return textView

        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view = container.findViewWithTag<View>("textView$position")
            container.removeView(view)
        }

    }


}