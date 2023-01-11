package com.example.data.repository

fun main() {
    val _n: Double? = readLine()?.toDoubleOrNull()
    var mPolovina: Int? = null
    var bPolovina: Int? = null

    var i: Int

    if(_n != null) {
        val n = _n.toInt()

        mPolovina = Math.floor(_n/2).toInt()
        bPolovina = n - mPolovina

        if(mPolovina != bPolovina) {

            while((bPolovina % mPolovina) != 0) {
                bPolovina++
                mPolovina--
            }
        }
    }

    print("$mPolovina $bPolovina")
}