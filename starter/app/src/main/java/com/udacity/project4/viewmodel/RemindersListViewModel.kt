package com.udacity.project4.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.udacity.project4.data.local.ReminderDataSource
import com.udacity.project4.data.model.Reminder

class RemindersListViewModel(dataSource: ReminderDataSource) : ViewModel() {

    // list that holds the reminder data to be displayed on the UI
    val remindersList: LiveData<List<Reminder>> = dataSource.getReminders()

}