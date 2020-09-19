package com.example.xh.kotlin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_leak.*

class LeakActivity : AppCompatActivity() {

    private val handler = object : Handler() {



        override fun handleMessage(msg: Message?) {
            Log.d("TAG", "start over --")
            super.handleMessage(msg)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_leak)
        Log.d("TAG", "onCreate() ${hashCode()}")

        btn1.setOnClickListener {
            startActivity(Intent(this, LeakActivity::class.java))
        }

        btn2.setOnClickListener {
            Log.d("TAG", "task start >>")
            handler.sendEmptyMessageDelayed(1, 1000 * 60 * 5)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
        Log.d("TAG", "onDestroy() ${hashCode()}")
    }

}