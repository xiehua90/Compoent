package com.example.xh.kotlin

import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        var array = Array(5) { it * it }

        for ((index, value) in array.withIndex()) {
            println("array[$index]: $value")
        }

        array.forEach {
            if (it == 4) return@forEach
            println(it)
        }
    }


}
