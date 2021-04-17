package com.udacity.project4.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.project4.data.dto.Result
import com.udacity.project4.data.model.Reminder

/**
 * Created by
 * Author: Ankush Bose
 * Date: 17,April,2021
 */
class FakeRemindersLocalRepositoryTest : ReminderDataSource {

    private val reminders: MutableList<Reminder> = mutableListOf()

    private val observableReminderItem = MutableLiveData<List<Reminder>>(reminders)

    override fun getReminders(): LiveData<List<Reminder>> {
        return observableReminderItem
    }

    override suspend fun saveReminder(reminder: Reminder) {
        reminders.add(reminder)
    }

    override suspend fun getReminder(id: String): Result<Reminder> {
        if (id.isNotEmpty()) {
            val reminder = Reminder(
                "Testing location",
                "Sample descriptio",
                "location name",
                22.647596,
                88.645856,
                "123456"
            )
            return Result.Success(reminder)
        }
        return Result.Error("No matching reminder found", null)
    }

    override suspend fun deleteAllReminders() {
        reminders.clear()
    }
}