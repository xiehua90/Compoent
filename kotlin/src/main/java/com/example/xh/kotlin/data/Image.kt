package com.example.xh.kotlin.data

import android.graphics.RectF
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi

@JsonClass(generateAdapter = true)
data class Image(var name: String, var value: String = "", var focusRect: RectF = RectF()) {

    val result: MutableList<String> by lazy {
        mutableListOf<String>()
    }

    constructor() : this("")

}