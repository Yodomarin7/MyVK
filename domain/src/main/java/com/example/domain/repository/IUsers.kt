package com.example.domain.repository

import com.example.domain.models.UserModel
import kotlinx.coroutines.flow.Flow

interface IUsers {
    suspend fun getUsersVK(
        grouId: String,
        offset: Int,
        count: Int,
        user: UserModel.Params): UserModel.ResponsParams

    suspend fun saveSex(i: Int)
    suspend fun getSex(): Int
    suspend fun saveLastSeen(i: Int)
    suspend fun getLastSeen(): Int

    suspend fun saveShowRed(b: Boolean)
    suspend fun getShowRed(): Boolean

    suspend fun saveShowYellow(b: Boolean)
    suspend fun getShowYellow(): Boolean

    suspend fun saveShowGreen(b: Boolean)
    suspend fun getShowGreen(): Boolean

    suspend fun getUsersDao(): Flow<MutableList<UserModel.Params>>
    suspend fun deleteOneDao(userId: Int)
    suspend fun updateOneDao(userParams: UserModel.Params)
    suspend fun saveShowBlack(b: Boolean)
    suspend fun getShowBlack(): Boolean
}