package com.example.data.repository

fun main() {
    val _n: Int? = readLine()?.toIntOrNull()
    val _s: String? = readLine()
    val _b: String? = readLine()

    var result: Int? = null

    if(_s!=null && _s!=null && _b!=null) {
        result = 0
        val words = _s.split(" ")

        var offset = 0
        var lastColor = 'N'

        if(_n == _b.length) {
            for(word in words) {
                val len = word.length - 1

                for(i in 0..len) {
                    val color = _b[offset + i]

                    if (i > 0) {
                        if(lastColor == color) {
                            result++
                            break
                        }
                    }

                    lastColor = color
                }

                offset += word.length
            }
        }
    }

    print(result)
}

//var result: Int? = null
//
//if(_1 != null && _2 != null) {
//    result = _1 + _2
//}