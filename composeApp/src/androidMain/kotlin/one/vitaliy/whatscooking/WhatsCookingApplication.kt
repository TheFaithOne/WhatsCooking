package one.vitaliy.whatscooking

import android.app.Application
import one.vitaliy.whatscooking.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class WhatsCookingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(this@WhatsCookingApplication)
        }
    }
}
