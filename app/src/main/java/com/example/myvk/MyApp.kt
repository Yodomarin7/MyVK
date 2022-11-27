package com.example.myvk

import android.app.Application
import android.util.Log
import com.example.myvk.di.AppComponent
import com.example.myvk.di.AppModule
import com.example.myvk.di.DaggerAppComponent
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKTokenExpiredHandler

class MyApp: Application()  {
    //val applicationScope = CoroutineScope(SupervisorJob())

    //val database by lazy { AppDatabase.getDatabase(this, applicationScope) }
   //val repository by lazy { GroupsRPS(database.groupDao()) }

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()

        VK.addTokenExpiredHandler(object: VKTokenExpiredHandler {
            override fun onTokenExpired() {
                VK.logout()

                Log.e("NEBUR", "Expired");
            }
        })
    }

}