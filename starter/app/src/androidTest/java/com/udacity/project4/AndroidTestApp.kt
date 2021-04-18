package com.udacity.project4

import android.app.Application
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

/**Created by
Author: Ankush Bose
Date: 17,April,2021
 **/
class AndroidTestApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {  }
    }

    override fun onTerminate() {
        stopKoin()
        super.onTerminate()
    }
}