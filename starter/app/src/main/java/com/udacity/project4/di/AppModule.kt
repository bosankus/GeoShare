package com.udacity.project4.di

import android.content.Context
import androidx.room.Room
import com.udacity.project4.data.local.RemindersDao
import com.udacity.project4.data.local.RemindersDatabase
import com.udacity.project4.data.local.RemindersLocalRepository
import com.udacity.project4.utils.REMINDER_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**Created by
Author: Ankush Bose
Date: 05,April,2021
 **/

/**The AppModule class is implemented to provide following dependencies
 * [RemindersDatabase], [RemindersDao], [RemindersLocalRepository]
 * */

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providesReminderDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            RemindersDatabase::class.java,
            REMINDER_DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun providesReminderDao(db: RemindersDatabase) = db.reminderDao()

    @Singleton
    @Provides
    fun providesReminderLocalRepository(dao: RemindersDao) = RemindersLocalRepository(dao)

}