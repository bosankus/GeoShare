package com.udacity.project4.data

import androidx.lifecycle.LiveData
import com.udacity.project4.data.model.Reminder
import com.udacity.project4.data.dto.Result

/**
 * Main entry point for accessing reminders data.
 */
interface ReminderDataSource {
    fun getReminders(): LiveData<List<Reminder>>
    suspend fun saveReminder(reminder: Reminder)
    suspend fun getReminder(id: String): Result<Reminder>
    suspend fun deleteAllReminders()
}