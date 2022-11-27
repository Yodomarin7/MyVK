package com.example.data

import com.example.data.sources.network.GroupRetrofit
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object AppRetrofit {
    val groupApi: GroupRetrofit.GroupApi by lazy {
        retrofitVk.create(GroupRetrofit.GroupApi::class.java)
    }

    private val retrofitVk: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.vk.com/method/")
            .client(
                OkHttpClient.Builder().addInterceptor(
                    HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)).build())
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
            .build()
    }
}