package com.example.domain.models

class GroupModel {

    data class Params(
        val id: Int = 0,
        val screenName: String = "",
        val name: String = "",
        val description: String = ""
    )

    sealed class ResponsParams {
        class Success(val params: Params): ResponsParams()
        class Failed(val code: Int, val message: String): ResponsParams()
    }
}

    
