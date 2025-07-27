package org.ronil.matchmate.utils

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.ronil.matchmate.di.AppModule
import org.ronil.matchmate.di.ViewModelModule


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(AppModule,ViewModelModule)
        }
    }
}