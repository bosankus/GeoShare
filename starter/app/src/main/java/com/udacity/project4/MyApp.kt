package com.udacity.project4

import android.app.Application
import com.udacity.project4.data.local.*
import com.udacity.project4.viewmodel.RemindersListViewModel
import com.udacity.project4.viewmodel.SaveReminderViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        val viewModelModule = module {

            viewModel { RemindersListViewModel(get() as ReminderDataSource) }
            single { SaveReminderViewModel(get(), get() as ReminderDataSource) }

            single { RemindersLocalRepository(get()) as ReminderDataSource }
            single { LocalDB.createRemindersDao(this@MyApp) }

        }

        startKoin {
            androidContext(this@MyApp)
            androidLogger()
            modules(listOf(viewModelModule))
        }
    }
}