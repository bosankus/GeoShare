package com.udacity.project4

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        /**
         * use Koin Library as a service locator
         */
        /*val myModule = module {
            viewModel { RemindersListViewModel(get() as ReminderDataSource) }
            single { SaveReminderViewModel(get(), get() as ReminderDataSource) }
            single { RemindersLocalRepository(get()) }
            single { LocalDB.createRemindersDao(this@MyApp) }
        }

        startKoin {
            androidContext(this@MyApp)
            modules(listOf(myModule))
        }*/
    }
}