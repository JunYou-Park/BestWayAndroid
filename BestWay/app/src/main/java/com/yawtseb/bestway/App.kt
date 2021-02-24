package com.yawtseb.bestway

import android.app.Application
import com.airetefacruo.myapplication.module.appModule
import com.airetefacruo.myapplication.module.repositoryModule
import com.airetefacruo.myapplication.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, repositoryModule, viewModelModule))
        }.koin
    }
}