package com.example.myvk.di

import com.example.data.repository.CommonRepository
import com.example.data.repository.GroupRepository
import com.example.data.repository.UserRepository
import com.example.domain.usecase.AddGroupUseCase
import com.example.domain.usecase.GetGroupsUseCase
import com.example.domain.usecase.GetUsersUseCase
import com.example.domain.usecase.RemoveGroupUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideGetGroupsUseCase(groupRepository: GroupRepository): GetGroupsUseCase {
        return GetGroupsUseCase(groupRepository)
    }

    @Provides
    fun provideRemoveGroupUseCase(groupRepository: GroupRepository): RemoveGroupUseCase {
        return RemoveGroupUseCase(groupRepository)
    }

    @Provides
    fun provideAddGroupUseCase(
        groupRepository: GroupRepository,
        commonRepository: CommonRepository
    ): AddGroupUseCase {
        return AddGroupUseCase(groupRepository, commonRepository)
    }

    @Provides
    fun provideGetUsersUseCase(userRepository: UserRepository): GetUsersUseCase {
        return GetUsersUseCase(userRepository)
    }
}