package com.example.xh.kotlin

import kotlinx.coroutines.*


fun add(a: Int, b: Int): Int {
    return a + b
}


//将表达式作为函数体，表达式的值作为返回类型自动推断类型;
fun add2(a: Int, b: Int) = a + b


//Unit 表示返回无意义的值 可以省略
//fun printAdd(a: Int, b: Int): Unit = print("${a + b}")
fun printAdd(a: Int, b: Int) = print("${a + b}")


fun main(args: Array<String>) {
//    listTest()
//    forTest()
//    rangeTest()
//    isTest()

    suspendTest()
}

fun suspendTest() {
//    GlobalScope.launch{
//        delay(1000L)
//        println("World!")
//    }
//    println("Hello,")
//    Thread.sleep(2000L)

//    GlobalScope.launch{
//        delay(1000L)
//        println("World")
//    }
//    println("Hello")
//    runBlocking{
//        delay(2000L)
//    }

//    runBlocking{
//        GlobalScope.launch{
//            delay(1000L)
//            println("World")
//        }
//        println("Hello")
//        delay(2000L)
//    }

//    runBlocking{
//        val job = GlobalScope.launch{
//            delay(1000L)
//            println("World ${Thread.currentThread().name}")
//        }
//
//        println("Hello ${Thread.currentThread().name}")
//        job.join()
//    }

//    runBlocking {
//        launch {
//            delay(1000L)
//            println("World ${Thread.currentThread().name}")
//        }
//        println("Hello ${Thread.currentThread().name}")
//    }


//    runBlocking{
//        launch{
//            delay(200L)
//            println("Task from runBlocking")
//        }
//        coroutineScope{
//            launch{
//                delay(500L)
//                println("Task from nested launch")
//            }
//
//            delay(100L)
//            println("Task from coroutine scope: ${Thread.currentThread().name}")
//        }
//
//        launch{
//            delay(100L)
//            println("Task from runBlocking2")
//        }
//
//        println("Corountine scope is over: ${Thread.currentThread().name}")
//    }

//    runBlocking {
//        val job = launch {
//            repeat(100) {
//                println("I'm sleeping $it")
//                delay(50L) // 等待一段时间
//                try {
//                } catch (e: CancellationException) {
//                    println("---->>>Exception CancellationException")
//                    e.printStackTrace()
//                }
//            }
//            println("job over")
//        }
//
//        delay(1300L)
//        println("main, I'am tired of waiting!")
//        job.cancel()
//        job.join()
//    }

//    runBlocking {
//        val startTime = System.currentTimeMillis()
//        val job = launch(Dispatchers.Default) {
//            var nextPrintTime = startTime
//            var i = 0
//            while (i < 5) { // 一个执行计算的循环，只是为了占用CPU
//                // 每秒打印消息两次
//                if (System.currentTimeMillis() >= nextPrintTime) {
//                    println("I'm sleeping ${i++} ...")
//                    nextPrintTime += 500L
//                }
//            }
//        }
//
//        delay(1300L) // 等待一段时间
//        println("main: I'm tired of waiting!")
//        job.cancelAndJoin() // 取消一个任务并且等待它结束
//        println("main: Now I can quit.")
//    }

//    runBlocking {
//        val startTime = System.currentTimeMillis()
//        val job = launch(Dispatchers.Default) {
//            var nextPrintTime = startTime
//            var i = 0
//            while (isActive) { // 可以被取消的计算循环
//                // 每秒打印消息两次
//                if (System.currentTimeMillis() >= nextPrintTime) {
//                    println("I'm sleeping ${i++} ...")
//                    nextPrintTime += 500L
//                }
//            }
//        }
//        delay(1300L) // 等待一段时间
//        println("main: I'm tired of waiting!")
//        job.cancelAndJoin() // 取消该任务并等待它结束
//        println("main: Now I can quit.")
//    }

//    runBlocking {
//        val job = launch {
//            try {
//                repeat(1000) { i ->
//                    println("I'm sleeping $i ...")
//                    delay(500L)
//                }
//            } finally {
//                withContext(NonCancellable) {
//                    println("I'm running finally")
//                    delay(1000L)
//                    println("And I've just delayed for 1 sec because I'm non-cancellable")
//                }
//            }
//        }
//        delay(1300L) // 延迟一段时间
//        println("main: I'm tired of waiting!")
//        job.cancelAndJoin() // 取消该任务并等待它结束
//        println("main: Now I can quit.")
//    }

//    runBlocking{
//        try{
//            withTimeout(1300){
//                repeat(100){
//                    println("I'm sleeping $it")
//                    delay(500L)
//                }
//            }
//        }catch (e: TimeoutCancellationException){
//            e.printStackTrace()
//            println("--->>>TimeoutCancellationException")
//        }
//    }

    runBlocking {
        val result = withTimeoutOrNull(1300L) {
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
            "Done" // 在它运行得到结果之前取消它
        }
        println("Result is $result") //通过withTimeoutOrNull返回 null 来进行超时操作，从而替代抛出一个异常
    }

    println("--end--")

}

fun bindTest() {
    val numberRegex = "\\d+".toRegex()
    println(numberRegex.matches("29"))

    val isNumber = numberRegex::matches
    println(isNumber("29"))

    var matches: (Regex, CharSequence) -> Boolean = Regex::matches  //未绑定方法的引用
    var matches1: (CharSequence) -> Boolean = numberRegex::matches  //绑定方法的引用

    val prop = "abc"::length
    println(prop.name)
    Thread.currentThread().name

}

fun isTest() {
    val string: String? = "Hello"

    println((string is String))
}

fun rangeTest() {
    var intProgression = 2.downTo(-10)
    println(intProgression)
    println(intProgression.reversed())

    intProgression = 2.rangeTo(8)
    println("$intProgression ${intProgression.last}")

    intProgression = intProgression.step(5)
    println("$intProgression ${intProgression.last}")
}

fun listTest() {
    var map: MutableMap<String, String> = HashMap()

    map["one"] = "1"
    println(map)

    val list = listOf(3, 1, 6, 2, 4, 9)
    val array = arrayListOf("Hello", "World")
    array.add("!")
    val mutableArray = mutableListOf(1, 3, 2, 8, 5)
    mutableArray.add(10)

    val numbers: MutableList<Int> = mutableListOf(1, 2, 3)
    val readOnlyView: List<Int> = numbers
    println(numbers)        // 输出 "[1, 2, 3]"
    numbers.add(4)
    println(readOnlyView)   // 输出 "[1, 2, 3, 4]"
//    readOnlyView.clear()    // -> 不能编译

    val strings = hashSetOf("a", "b", "c", "c")
    assert(strings.size == 3)
    println(numbers.toList())

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

