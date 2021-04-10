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
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
class SaveReminderViewModel(
    app: Application,
    private val dataSource: RemindersLocalRepository
) :
    AndroidViewModel(app) {

    private val context = app.applicationContext

    private var _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> get() = _showLoading

    private var _showMessage = MutableLiveData<String?>()
    val showMessage: LiveData<String?> get() = _showMessage

    private var _isReminderSaved = MutableLiveData(false)
    val isReminderSaved: LiveData<Boolean> get() = _isReminderSaved

    val reminderTitle = MutableLiveData<String?>()
    val reminderDescription = MutableLiveData<String?>()

    var location = MutableLiveData<String?>()
    var latitude = MutableLiveData<Double?>()
    var longitude = MutableLiveData<Double?>()

    fun validateAndSaveReminder(reminderData: Reminder) {
        if (validateEnteredData(reminderData)) {
            saveReminder(reminderData)
        }
    }


    private fun saveReminder(reminderData: Reminder) {
        _showLoading.value = true
        try {
            viewModelScope.launch {
                dataSource.saveReminder(reminderData)
                _isReminderSaved.value = true
                _showLoading.value = false
                _showMessage.value = "Reminded saved successfully"
            }
        } catch (e: Exception) {
            _showLoading.value = false
            _showMessage.value = "Couldn't save reminder due to: ${e.localizedMessage}"
        }
    }


    private fun validateEnteredData(reminderData: Reminder): Boolean {
        return if (reminderData.latitude == 0.0 || reminderData.longitude == 0.0) {
            _showMessage.value = context.resources.getString(R.string.select_location)
            false
        } else if (reminderData.title?.isEmpty() == true) {
            _showMessage.value = context.resources.getString(R.string.select_title)
            false
        } else true
    }
}