package com.example.xh.kotlin


fun add(a: Int, b: Int): Int {
    return a + b
}


//将表达式作为函数体，表达式的值作为返回类型自动推断类型;
fun add2(a: Int, b: Int) = a + b


//Unit 表示返回无意义的值 可以省略
//fun printAdd(a: Int, b: Int): Unit = print("${a + b}")
fun printAdd(a: Int, b: Int) = print("${a + b}")


fun main(args: Array<String>) {
//    printAdd(2, 3)
//    Man().toString()
//    forTest()

//    listTest()

    val list = listOf<Int>(3, 1, 6, 2, 4, 9)

//    println(list)
//    list.forEach { println(it) }
    list.sortedBy { it % 5 }
            .forEach { println(it) }

    list.apply
}

fun listTest() {
    var map: MutableMap<String, String> = HashMap()

    map["one"] = "1"
    println(map)


}

fun forTest() {
    var list = listOf("apple", "orange", "banana")

    for (fruit in list) {
        println(fruit)
    }
    println()

    for (index in list.indices) {
        println("item at $index is ${list[index]}")
    }
    println()

    val range = 0..10
    for (i in 0 until 10 step 1) {
        println(i)
    }
    println()
    for (i in 10 downTo 0 step 3) {
        println(i)
    }

    println("$list contain apple: ${"apple" in list}")
    println("$range contain 2: ${10 in range}")

    list.filter { it.startsWith("a") }
            .sortedBy { it }
    list.forEach { }

}


fun describe(obj: Any): String = when (obj) {
    1 -> "one"
    "Hello" -> "Hello"
    obj is String -> "String"
    obj !is Long -> "Not a Long"
    else -> "Unknown type"


}

class Man {
    var age: Int? = null
    var name: String = "name"
        set(value) {
            print("set()")
            field = value
        }
        get() {
            println("get()")
            return field
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
        name = "xiehua"
        val test = name

        table;
        _table

        return super.toString()
    }
}