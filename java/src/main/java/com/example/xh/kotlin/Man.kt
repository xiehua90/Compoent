package com.example.xh.kotlin

open class Animal(var name: String)


open class Man(name: String, var age: Int) : Animal(name) {

    init {
        println("main initialized ${toString()}")
    }
    private var _table: Map<String, Int>? = null
    var table: Map<String, Int>? = null
        get() {
            if (_table == null) {
                _table = HashMap()
            }
            return _table ?: throw AssertionError("Set to null by another thread")
        }


    override fun toString(): String {
//        println("name:$name, age:$age")
        return super.toString()
    }
}

open class Bird(name: String) : Animal(name) {
    var age: Int? = null

    constructor(name: String, age: Int) : this(name) {
        this.age = age
    }

    override fun toString(): String {
        println(age)
        return super.toString()
    }

}