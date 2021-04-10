package com.udacity.project4.di

import android.app.Application
import androidx.room.Room
import com.udacity.project4.data.local.RemindersDao
import com.udacity.project4.data.local.RemindersDatabase
import com.udacity.project4.data.local.RemindersLocalRepository
import com.udacity.project4.utils.REMINDER_DATABASE_NAME
import com.udacity.project4.viewmodel.RemindersListViewModel
import com.udacity.project4.viewmodel.SaveReminderViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**Created by
Author: Ankush Bose
Date: 09,April,2021
 **/

/**The AppModule class is implemented to inject following dependencies
 * [RemindersDatabase], [RemindersDao], [RemindersLocalRepository]
 * */

val databaseModule = module {

    fun provideReminderDatabase(application: Application): RemindersDatabase {
        return Room.databaseBuilder(
            application,
            RemindersDatabase::class.java,
            REMINDER_DATABASE_NAME
        ).build()
    }

    fun providesReminderDao(db: RemindersDatabase): RemindersDao {
        return db.reminderDao()
    }

    single { provideReminderDatabase(androidApplication()) }
    single { providesReminderDao(get()) }
}

val viewModelModule = module {

    single { RemindersLocalRepository(get()) }
    viewModel { RemindersListViewModel(get()) }
    viewModel { SaveReminderViewModel(androidApplication(), get()) }
}

