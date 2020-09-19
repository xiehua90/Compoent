package com.example.xh.kotlin.data

import com.google.gson.Gson
import org.junit.Test

import org.junit.Assert.*

class ImageTest {

    @Test
    fun getName() {
        val gson = Gson()

        val bean = Image("abc.jpg", "ABC123")

        val json = gson.toJson(bean)
        println(json)

        val temp = gson.fromJson(json, Image::class.javaObjectType)
        assertNotNull(temp)
    }
}