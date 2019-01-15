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
//    val man = Man("xiehua", 20)
//    println(man)
//    val bird = Bird("鸽子", 2)
//    println(bird)


//    printAdd(2, 3)
//    Man().toString()
//    forTest()

//    listTest()

    val list = listOf<Int>(3, 1, 6, 2, 4, 9)

//    println(list)
//    list.forEach { println(it) }
//    list.sortedBy { it % 5 }
//            .forEach { println(it) }

//    list.apply



    fun add(a: Int, b: Int) = a + b

    var sum: (a: Int, b: Int) -> Int

    sum = { a, b -> a + b }
    println(sum(3, 5))
    sum = fun(a: Int, b: Int): Int {
        return a + b
    }
    println(sum(5, 5))
    sum = ::add
    println(sum(6, 10))

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

