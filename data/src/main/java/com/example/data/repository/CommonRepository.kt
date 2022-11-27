package com.example.data.repository

import com.example.data.sources.preference.MainSharedPreferences
import com.example.domain.repository.ICommon

class CommonRepository(
    private val mainSharedPreferences: MainSharedPreferences
): ICommon {
    override fun getVkAuthToken(): String {
        return mainSharedPreferences.getVkAuthToken()
    }

    override fun saveVkAuthToken(token: String) {
        mainSharedPreferences.saveVkAuthToken(token)
    }

}