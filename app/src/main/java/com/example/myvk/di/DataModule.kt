package com.example.myvk.di

import android.content.Context
import com.example.data.repository.CommonRepository
import com.example.data.repository.GroupRepository
import com.example.data.repository.UserRepository
import com.example.data.sources.group.room.AppDatabase
import com.example.data.sources.network.GroupRetrofit
import com.example.data.sources.network.UserVK
import com.example.data.sources.preference.MainSharedPreferences
import com.example.data.sources.preference.UserParamsSharedPreferences
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideDB(context: Context): AppDatabase {
        return AppDatabase.getDatabase(context.applicationContext)
    }

    @Provides
    fun provideGroupRetrofit(): GroupRetrofit {
        return GroupRetrofit()
    }

    @Provides
    fun provideGroupRepository(
        db: AppDatabase,
        groupRetrofit: GroupRetrofit): GroupRepository {
        return GroupRepository(db.groupDao(), groupRetrofit)
    }

    @Provides
    fun provideCommonRepository(
        sharedPreferences: MainSharedPreferences
    ): CommonRepository {
        return CommonRepository(sharedPreferences)
    }

    @Provides
    fun provideMainSharedPreferences(context: Context): MainSharedPreferences {
        return MainSharedPreferences(context)
    }

    @Provides
    fun provideUserRepositoryUserRepository(
        db: AppDatabase,
        vk: UserVK,
        userParamsSharedPreferences: UserParamsSharedPreferences
    ): UserRepository {
        return UserRepository(db.usersDao(), vk, userParamsSharedPreferences)
    }

    @Provides
    fun provideUserVK(): UserVK {
        return UserVK()
    }

    @Provides
    fun provideUserParamsSharedPreferences(context: Context): UserParamsSharedPreferences {
        return UserParamsSharedPreferences(context.applicationContext)
    }
}