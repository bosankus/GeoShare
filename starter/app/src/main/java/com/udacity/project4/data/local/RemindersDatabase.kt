package com.udacity.project4.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.udacity.project4.data.model.Reminder

/**
 * The Room Database that contains the reminders table.
 */
@Database(entities = [Reminder::class], version = 1, exportSchema = false)
abstract class RemindersDatabase : RoomDatabase() {

    abstract fun reminderDao(): RemindersDao
}