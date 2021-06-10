package com.test.diproject

import android.content.Context
import android.preference.PreferenceManager
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.test.diproject.api.ApiService
import com.test.diproject.api.CallbackApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class AppInjector private constructor(private val context: Context) {

    companion object {

        private lateinit var INSTANCE: AppInjector
        private var initialized = false

        fun init(context: Context) {
            INSTANCE = AppInjector(context)
            initialized = true
        }

        fun getMainRepository(): MainRepository = INSTANCE.mainRepository
    }

    private val sharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }

    private val mainRepository by lazy {
        MainRepositoryImpl(apiService, callbackApiService)
    }

    private val okHttp by lazy {
        OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .build()
    }

    private val apiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .client(okHttp)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    private val callbackApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CallbackApiService::class.java)
    }
}