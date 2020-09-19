package com.example.xh.kotlin.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.xh.kotlin.BaseFragement
import com.example.xh.kotlin.LeakActivity
import com.example.xh.kotlin.R
import kotlinx.android.synthetic.main.fragment_dialog.*

class DialogFragment : BaseFragement() {
    override fun getLayoutResId(): Int {
        return R.layout.fragment_dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = arrayOf("苹果", "梨子", "香蕉", "苹果", "梨子", "香蕉", "苹果", "梨子", "香蕉", "苹果", "梨子", "香蕉")

        val booleanArray = booleanArrayOf(false, true, false, false, true, false, false, true, false, false, true, false)
        button.setOnClickListener {
            val itemString: Array<CharSequence> = arrayOf("item one", "item Two")
            AlertDialog.Builder(activity)
                    .setTitle("title")
                    .setItems(itemString) { _: DialogInterface, which: Int ->
                        Toast.makeText(activity, "${itemString[which]}", Toast.LENGTH_SHORT).show()
//                            d.dismiss()
                    }
                    .setMultiChoiceItems(list, booleanArray) { t1, t2, t3 ->
                        Log.d("TAG", "$t2")
                    }
                    .setNeutralButton("选择全部", null)
                    .setPositiveButton("确定", null)
                    .setNegativeButton("取消", null)
                    .show()
        }

        btn2.setOnClickListener {
            startActivity(Intent(activity, LeakActivity::class.java))
        }


    }
}