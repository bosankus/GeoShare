package com.udacity.project4.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.project4.R
import com.udacity.project4.data.local.RemindersLocalRepository
import com.udacity.project4.data.model.Reminder
import com.udacity.project4.view.reminderslist.ReminderDataItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
@SuppressLint("StaticFieldLeak")
class SaveReminderViewModel @Inject constructor(
    app: Application,
    private val dataSource: RemindersLocalRepository
) :
    AndroidViewModel(app) {

    private val context = app.applicationContext

    private var _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> get() = _showLoading

    private var _showMessage = MutableLiveData<String>()
    val showMessage: LiveData<String> get() = _showMessage

    private var _isReminderSaved = MutableLiveData(false)
    val isReminderSaved: LiveData<Boolean> get() = _isReminderSaved

    val reminderTitle = MutableLiveData<String?>()
    val reminderDescription = MutableLiveData<String?>()

    fun validateAndSaveReminder(reminderData: ReminderDataItem) {
        if (validateEnteredData(reminderData)) {
            saveReminder(reminderData)
        }
    }


    private fun saveReminder(reminderData: ReminderDataItem) {
        _showLoading.value = true
        try {
            viewModelScope.launch {
                dataSource.saveReminder(
                    Reminder(
                        reminderData.title,
                        reminderData.description,
                        reminderData.location,
                        reminderData.latitude,
                        reminderData.longitude,
                        reminderData.id
                    )
                )
                _isReminderSaved.value = true
                _showLoading.value = false
                _showMessage.value = "Reminded saved successfully"
            }
        } catch (e: Exception) {
            _showLoading.value = false
            _showMessage.value = "Couldn't save reminder due to: ${e.localizedMessage}"
        }
    }

    private fun validateEnteredData(reminderData: ReminderDataItem): Boolean {
        return if (reminderData.latitude == 0.0 || reminderData.longitude == 0.0) {
            _showMessage.value = context.resources.getString(R.string.select_location)
            false
        } else if (reminderData.title?.isEmpty() == true) {
            _showMessage.value = context.resources.getString(R.string.select_title)
            false
        } else true
    }
}