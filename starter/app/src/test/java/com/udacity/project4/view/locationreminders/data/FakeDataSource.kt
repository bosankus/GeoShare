package com.udacity.project4.view.locationreminders.data

import androidx.lifecycle.LiveData
import com.udacity.project4.data.dto.Result
import com.udacity.project4.data.local.ReminderDataSource
import com.udacity.project4.data.model.Reminder

//Use FakeDataSource that acts as a test double to the LocalDataSource
class FakeDataSource : ReminderDataSource {

//    TODO: Create a fake data source to act as a double to the real data source

    override fun getReminders(): LiveData<List<Reminder>> {
        TODO("Return the reminders")
    }

    override suspend fun saveReminder(reminder: Reminder) {
        TODO("save the reminder")
    }

    override suspend fun getReminder(id: String): Result<Reminder> {
        TODO("return the reminder with the id")
    }

    override suspend fun deleteAllReminders() {
        TODO("delete all the reminders")
    }


}