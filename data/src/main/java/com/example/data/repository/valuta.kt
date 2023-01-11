package com.example.data.repository

fun main() {
    val _valuta: String? = readLine()
    val _summa: String? = readLine()

    var _a: Int
    var _b: Int
    var _c: Int

    var _x: Int
    var _y: Int
    var _z: Int

    val valuta = _valuta!!.split(" ")
    _a = valuta[0].toInt()
    _b = valuta[1].toInt()
    _c = valuta[2].toInt()

    val summa = _summa!!.split(" ")
    _x = summa[0].toInt()
    _y = summa[1].toInt()
    _z = summa[2].toInt()


}