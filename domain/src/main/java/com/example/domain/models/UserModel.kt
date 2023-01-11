package com.example.domain.models

class UserModel {

    data class Params(
        val id: Int = 0,
        val sex: Int = 0,
        val lastSeen: Int = 0,
        val firstName: String = "",
        val lastName: String = "",
        var used: Used = UserModel.Used.NONE
    )

    enum class Used {
        NONE, RED, YELLOW, GREEN, BLACK
    }

    sealed class ResponsParams {
        class Success(val params: MutableList<Params>, val offset: Int): ResponsParams()
        class Failed(val code: Int, val message: String): ResponsParams()
    }
}