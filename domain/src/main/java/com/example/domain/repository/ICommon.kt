package com.example.domain.repository

interface ICommon {
    fun getVkAuthToken(): String
    fun saveVkAuthToken(token: String)
}