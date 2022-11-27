package com.example.data.sources.preference

import android.content.Context

private const val SHARED_PREFS_NAME = "userParamsDataSource"
private const val SEX = "sex"
private const val LAST_SEEN = "last_Seen"
private const val SHOW_RED = "show_red"
private const val SHOW_YELLOW = "show_yellow"
private const val SHOW_GREEN = "show_green"

class UserParamsSharedPreferences(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    fun getShowRed(): Boolean {
        return sharedPreferences.getBoolean(SHOW_RED, true)
    }
    fun saveShowRed(b: Boolean) {
        sharedPreferences.edit().putBoolean(SHOW_RED, b).apply()
    }

    fun getShowYellow(): Boolean {
        return sharedPreferences.getBoolean(SHOW_YELLOW, true)
    }
    fun saveShowYellow(b: Boolean) {
        sharedPreferences.edit().putBoolean(SHOW_YELLOW, b).apply()
    }

    fun getShowGreen(): Boolean {
        return sharedPreferences.getBoolean(SHOW_GREEN, true)
    }
    fun saveShowGreen(b: Boolean) {
        sharedPreferences.edit().putBoolean(SHOW_GREEN, b).apply()
    }

    fun getLastSeen(): Int {
        return sharedPreferences.getInt(LAST_SEEN, 0)
    }
    fun saveLastSeen(i: Int) {
        sharedPreferences.edit().putInt(LAST_SEEN, i).apply()
    }

    fun getSex(): Int {
        return sharedPreferences.getInt(SEX, 0)
    }
    fun saveSex(i: Int) {
        sharedPreferences.edit().putInt(SEX, i).apply()
    }
}