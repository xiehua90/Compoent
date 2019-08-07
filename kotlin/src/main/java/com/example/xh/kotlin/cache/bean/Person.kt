package com.example.xh.kotlin.cache.bean

import timber.log.Timber


class Person {
    var name: String? = null
    var gender: String? = null
    var age: Int? = null


    constructor(name: String?, gender: String?, age: Int?) {
        this.name = name
        this.gender = gender
        this.age = age

        Timber.d("name=$name, gender=$gender, age=$age")
    }
}