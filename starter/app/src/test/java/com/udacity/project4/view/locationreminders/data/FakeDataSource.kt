package com.udacity.project4.view.locationreminders.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.project4.data.dto.Result
import com.udacity.project4.data.local.ReminderDataSource
import com.udacity.project4.data.model.Reminder

//Use FakeDataSource that acts as a test double to the LocalDataSource
class FakeDataSource : ReminderDataSource {

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