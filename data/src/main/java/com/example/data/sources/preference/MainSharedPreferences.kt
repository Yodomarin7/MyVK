package com.example.data.sources.preference

import android.content.Context

private const val SHARED_PREFS_NAME = "vkDataSource"
private const val AUTH_TOKEN_VK = "authTokenVk"

class MainSharedPreferences(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    fun getVkAuthToken(): String {
        return sharedPreferences.getString(AUTH_TOKEN_VK, "") ?: ""
    }

    fun saveVkAuthToken(token: String) {
        sharedPreferences.edit().putString(AUTH_TOKEN_VK, token).apply()
    }
}