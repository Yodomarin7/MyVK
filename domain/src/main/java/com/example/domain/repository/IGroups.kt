package com.example.domain.repository

import com.example.domain.models.GroupModel
import kotlinx.coroutines.flow.Flow

interface IGroups {
    suspend fun getGroupsDao(): Flow<MutableList<GroupModel.Params>>
    suspend fun addOneDao(groupParams: GroupModel.Params)
    suspend fun deleteOneDao(groupId: Int)

    suspend fun getByIdRetrofit(groupId: String, token: String): GroupModel.ResponsParams
}