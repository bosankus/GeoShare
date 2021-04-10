package com.udacity.project4

import android.app.Application
import com.google.firebase.FirebaseApp
import com.udacity.project4.di.databaseModule
import com.udacity.project4.di.viewModelModule
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        FirebaseApp.initializeApp(this)
        startKoin {
            androidContext(this@MyApp)
            androidLogger()
            modules(listOf(databaseModule, viewModelModule))
        }
    }
}