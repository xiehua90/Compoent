package com.example.xh.kotlin.fragment

import android.os.Bundle
import android.view.View
import com.example.xh.kotlin.BaseFragement
import com.example.xh.kotlin.R
import com.example.xh.kotlin.cache.bean.Person
import kotlinx.android.synthetic.main.fragment_timber.*
import timber.log.Timber

class Timber: BaseFragement(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id){
            R.id.btn1 -> {
                Timber.d("Hello World")
            }

            R.id.btn2 ->{
                val person = Person("xiehua", "man", 20)

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())


    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_timber
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn1.setOnClickListener(this)
        btn2.setOnClickListener(this)

    }
}