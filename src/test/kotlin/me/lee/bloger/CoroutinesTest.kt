package me.lee.bloger

import kotlinx.coroutines.*

fun main2() {
//    println("start")
//    GlobalScope.launch() {
//        val result = doSomething()
//        println(result)
//    }
//    runBlocking {
//        delay(4_000L)
//    }
//    println("done")

    val deferred = (1..1_000_000).map {
        GlobalScope.async {
            delay(1_000L)
            it
        }
    }
    runBlocking {
        val sum = deferred.sumBy { it.await() }
        println(sum)
    }

//    var sum = 0
//    for (i in 0..1_000_000) {
//        sum += i
//    }
//    println(sum)

}

fun main() = runBlocking { // this: CoroutineScope
    launch {
        delay(200L)
        println("Task from runBlocking")
    }

    coroutineScope { // 创建一个协程作用域
        launch {
            delay(500L)
            println("Task from nested launch")
        }

        delay(100L)
        println("Task from coroutine scope") // 这一行会在内嵌 launch 之前输出
    }

    println("Coroutine scope is over") // 这一行在内嵌 launch 执行完毕后才输出
}

suspend fun doSomething(): String {
    delay(3_000L)
    return "result"
}