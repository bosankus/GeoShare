package com.udacity.project4.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import com.udacity.project4.base.BaseViewModel
import com.udacity.project4.data.ReminderDataSource
import com.udacity.project4.data.model.Reminder

class RemindersListViewModel(app: Application, dataSource: ReminderDataSource) :
    BaseViewModel(app) {

    // list that holds the reminder data to be displayed on the UI
    val remindersList: LiveData<List<Reminder>> = dataSource.getReminders()

    /**
     * Inform the user that there's not any data if the remindersList is empty
     */
    private fun invalidateShowNoData() {
        showNoData.value = remindersList.value == null || remindersList.value!!.isEmpty()
    }
}