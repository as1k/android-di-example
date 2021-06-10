package com.test.diproject

import android.app.Application
import com.facebook.stetho.Stetho

class DIApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        AppInjector.init(this)
    }
}