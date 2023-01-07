package com.example.myvk.di

import android.content.Context
import com.example.data.repository.UserRepository
import com.example.domain.usecase.AddGroupUseCase
import com.example.domain.usecase.GetGroupsUseCase
import com.example.domain.usecase.GetUsersUseCase
import com.example.domain.usecase.RemoveGroupUseCase
import com.example.myvk.AddViewModelFactory
import com.example.myvk.GetUserViewModelFactory
import com.example.myvk.viewmodels.GetViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class AppModule(val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun provideGetGroupVMF(
        getGroupsUseCase: GetGroupsUseCase,
        removeGroupUseCase: RemoveGroupUseCase
    ): GetViewModelFactory {
        return GetViewModelFactory(
            getGroupsUseCase = getGroupsUseCase,
            removeGroupUseCase = removeGroupUseCase
        )
    }

    @Provides
    fun provideGetUserVMF(
        getUsersUseCase: GetUsersUseCase,
        userRepository: UserRepository
    ): GetUserViewModelFactory {
        return GetUserViewModelFactory(
            getUsersUC = getUsersUseCase,
            userRepository = userRepository
        )
    }

    @Provides
    fun provideAddViewModelFactory(
        addGroupUC: AddGroupUseCase
    ): AddViewModelFactory {
        return AddViewModelFactory(addGroupUC = addGroupUC)
    }

}