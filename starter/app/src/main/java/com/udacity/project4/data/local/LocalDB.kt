package com.udacity.project4.data.local

import android.content.Context
import androidx.room.Room
import com.udacity.project4.utils.REMINDER_DATABASE_NAME


/**
 * Singleton class that is used to create a reminder db
 */
object LocalDB {

    /**
     * static method that creates a reminder class and returns the DAO of the reminder
     */
    fun createRemindersDao(context: Context): RemindersDao {
        return Room.databaseBuilder(
            context.applicationContext,
            RemindersDatabase::class.java, REMINDER_DATABASE_NAME
        ).build().reminderDao()
    }

}