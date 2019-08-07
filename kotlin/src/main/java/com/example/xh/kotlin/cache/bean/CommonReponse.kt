package com.example.xh.kotlin.cache.bean

class CommonReponse<T> {

    var error: Int? = null
    var data: T? = null

    constructor(error: Int?, data: T?) {
        this.error = error
        this.data = data
    }
}