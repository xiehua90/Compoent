package com.example.xh.kotlin


import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.example.xh.kotlin", appContext.packageName)

        //        val jsonStr = "{\"name\":\"Victor Apoyan\"}"
        //        val client = OkHttpClient.Builder()
        //                .addInterceptor {
        //                    val body = ResponseBody.create(MediaType.parse("application/json"), jsonStr.toByteArray())
        //                    val response = Response.Builder()
        //                            .code(200)
        //                            .message(jsonStr)
        //                            .request(it.request())
        //                            .protocol(Protocol.HTTP_1_0)
        //                            .body(body)
        //                            .addHeader("content-type", "application/json")
        //                            .build()
        //                    response
        //                }
        //                .build()
    }
}
