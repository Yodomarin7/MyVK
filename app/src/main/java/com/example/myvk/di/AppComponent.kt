package com.example.myvk.di

import com.example.myvk.screens.AddGroupFragment
import com.example.myvk.screens.MainActivity
import com.example.myvk.screens.UserParamsFragment
import dagger.Component

@Component(modules = [AppModule::class, DomainModule::class, DataModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(userParamsFragment: UserParamsFragment)
    fun inject(addGroupFragment: AddGroupFragment)
}