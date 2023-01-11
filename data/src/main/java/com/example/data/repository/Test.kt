package com.example.data.repository

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.regex.Matcher
import java.util.regex.Pattern

fun main(): Unit = runBlocking {
    launch {
        delay(2000L)
        println("A")
    }
    launch {
        delay(1000L)
        println("B")
    }

    val number = "\\d+"
    val pattern: Pattern = Pattern.compile(number)
    val matcher: Matcher = pattern.matcher("")

    println("Done")

//    var _1: Int
//    var _2: Int

//    while (true) {
//        println("Введите первое число:")
//        val _t: Int? = readLine()?.toIntOrNull()
//
//        if(_t == null) println("Ошибочный ввод")
//        else {
//            _1 = _t
//            break
//        }
//    }
//
//    while (true) {
//        println("Введите второе число:")
//        val _t: Int? = readLine()?.toIntOrNull()
//
//        if(_t == null) println("Ошибочный ввод")
//        else {
//            _2 = _t
//            break
//        }
//    }

//    println("Сумма двух чисел:")
}