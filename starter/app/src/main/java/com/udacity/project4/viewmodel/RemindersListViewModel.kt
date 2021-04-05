package com.udacity.project4.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.udacity.project4.data.local.RemindersLocalRepository
import com.udacity.project4.data.model.Reminder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RemindersListViewModel @Inject constructor(dataSource: RemindersLocalRepository) :
    ViewModel() {

    // list that holds the reminder data to be displayed on the UI
    val remindersList: LiveData<List<Reminder>> = dataSource.getReminders()

    /**
     * Inform the user that there's not any data if the remindersList is empty
    private fun invalidateShowNoData() {
    showNoData.value = remindersList.value == null || remindersList.value!!.isEmpty()
    }*/
}