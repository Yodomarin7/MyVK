package com.example.domain.usecase

import com.example.domain.repository.IGroups

class RemoveGroupUseCase(
    private val groupsRepository: IGroups
) {
    suspend fun run(groupId: Int) {
        groupsRepository.deleteOneDao(groupId)
    }
}