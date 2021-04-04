package com.udacity.project4.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.project4.data.model.Reminder

/**
 * Data Access Object for the reminders table.
 */
@Dao
interface RemindersDao {
    /**
     * @return all reminders.
     */
    @Query("SELECT * FROM reminders")
    fun getReminders(): LiveData<List<Reminder>>

    /**
     * @param reminderId the id of the reminder
     * @return the reminder object with the reminderId
     */
    @Query("SELECT * FROM reminders where entry_id = :reminderId")
    suspend fun getReminderById(reminderId: String): Reminder?

    /**
     * Insert a reminder in the database. If the reminder already exists, replace it.
     *
     * @param reminder the reminder to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveReminder(reminder: Reminder)

    /**
     * Delete all reminders.
     */
    @Query("DELETE FROM reminders")
    suspend fun deleteAllReminders()

}