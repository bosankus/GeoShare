package com.udacity.project4

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import com.udacity.project4.data.local.RemindersDao
import com.udacity.project4.data.local.RemindersDatabase
import com.udacity.project4.data.local.RemindersLocalRepository
import com.udacity.project4.utils.REMINDER_DATABASE_NAME

/**Created by
Author: Ankush Bose
Date: 14,April,2021
 **/

object ServiceLocator {

    @Volatile
    var repository: RemindersLocalRepository? = null
        @VisibleForTesting set

    var database: RemindersDatabase? = null

    fun provideReminderLocalRepository(context: Context): RemindersLocalRepository {
        synchronized(this) {
            return repository ?: createReminderLocalRepository(context)
        }
    }

    private fun createReminderLocalRepository(context: Context): RemindersLocalRepository {
        val newRepo = RemindersLocalRepository(provideReminderDao(context))
        repository = newRepo
        return newRepo
    }

    private fun provideReminderDao(context: Context): RemindersDao {
        return database?.reminderDao() ?: createReminderDatabase(context).reminderDao()
    }

    private fun createReminderDatabase(context: Context): RemindersDatabase {
        val db = Room.databaseBuilder(
            context.applicationContext,
            RemindersDatabase::class.java,
            REMINDER_DATABASE_NAME
        ).build()
        database = db
        return db
    }

}