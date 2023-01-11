package com.example.domain.usecase

import com.example.domain.models.GroupModel
import com.example.domain.repository.IGroups
import kotlinx.coroutines.flow.Flow

//UseCase
class GetGroupsUseCase(private val repository: IGroups) {

    suspend fun run(): Flow<MutableList<GroupModel.Params>> {
        return repository.getGroupsDao()
    }

    suspend fun set(group: GroupModel.Params) {
        repository.addOneDao(group)
    }
}