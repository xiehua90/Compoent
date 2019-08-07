package com.example.xh.kotlin.cache

import com.example.xh.kotlin.cache.bean.CommonReponse
import com.example.xh.kotlin.cache.bean.Person
import io.reactivex.Observable
import io.rx_cache2.DynamicKey
import io.rx_cache2.LifeCache
import io.rx_cache2.ProviderKey
import io.rx_cache2.Reply
import java.util.concurrent.TimeUnit
import javax.xml.datatype.DatatypeConstants.SECONDS


interface Providers {

    @ProviderKey("/person_list")
    fun providePersonList(provide: Observable<CommonReponse<List<Person>>>, page: DynamicKey): Observable<Reply<CommonReponse<List<Person>>>>


    @ProviderKey("/person_list2")
    @LifeCache(timeUnit = TimeUnit.SECONDS,duration = 2)
    fun providePersonList2(provide: Observable<CommonReponse<List<Person>>>, page: DynamicKey): Observable<CommonReponse<List<Person>>>


    @ProviderKey("/person_list3")
    fun providePersonList3(provide: Observable<List<Person>>, page: DynamicKey): Observable<Reply<List<Person>>>

}