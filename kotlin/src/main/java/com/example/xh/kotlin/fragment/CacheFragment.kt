package com.example.xh.kotlin.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.xh.kotlin.BaseFragement
import com.example.xh.kotlin.R
import com.example.xh.kotlin.cache.Providers
import com.example.xh.kotlin.cache.bean.CommonReponse
import com.example.xh.kotlin.cache.bean.Person
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.rx_cache2.DynamicKey
import io.rx_cache2.internal.RxCache
import io.victoralbertos.jolyglot.GsonSpeaker
import kotlinx.android.synthetic.main.fragment_cache.*

class CacheFragment : BaseFragement() {
    val provide: Providers by lazy {
        RxCache.Builder().persistence(activity!!.cacheDir, GsonSpeaker()).using(Providers::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_cache
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn1.setOnClickListener { it -> fetchData() }
    }


    fun fetchData() {
//        provide.providePersonList2(mockList(), DynamicKey("one"))
//                .map { it ->
//                    val gson = Gson()
//                    val string = gson.toJson(it)
//                    Log.d("TAG", string!!)
//                    it.data }
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe { it -> tv1.text = "Hello World"}

        mockList().map { it ->
            Log.d("TAG", it.toString())
            it
        }
                .subscribe(System.out::println)


    }

    fun mockList(): Observable<CommonReponse<List<Person>>> {
        Log.d("TAG", "MockData()")
        return Observable.create { e: ObservableEmitter<CommonReponse<List<Person>>> ->
            var list = mutableListOf<Person>()

            list.add(Person("xiehua", "male", 29))
            list.add(Person("fuxiang", "female", 29))
            list.add(Person("lianzhi", "female", 24))

            val response = CommonReponse<List<Person>>(200, list)
            e.onNext(response)
            e.onComplete()
        }
    }

    fun mockList2(): Observable<List<Person>> {
        Log.d("TAG", "MockData()")
        return Observable.create { e: ObservableEmitter<List<Person>> ->
            var list = mutableListOf<Person>()

            list.add(Person("xiehua", "male", 29))
            list.add(Person("fuxiang", "female", 29))
            list.add(Person("lianzhi", "female", 24))

            val response = CommonReponse<List<Person>>(200, list)
            e.onNext(list)
            e.onComplete()
        }
    }


}