package com.udacity.project4.utils

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import com.udacity.project4.data.dto.Result
import com.udacity.project4.data.local.RemindersLocalRepository
import com.udacity.project4.data.model.Reminder
import com.udacity.project4.view.reminderslist.ReminderDataItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class GeofenceTransitionsJobIntentService : JobIntentService(), CoroutineScope {

    @Inject
    lateinit var remindersLocalRepository: RemindersLocalRepository

    private var coroutineJob: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + coroutineJob

    companion object {
        private const val JOB_ID = 573

        //        TODO: call this to start the JobIntentService to handle the geofencing transition events
        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(
                context,
                GeofenceTransitionsJobIntentService::class.java, JOB_ID,
                intent
            )
        }
    }

    override fun onHandleWork(intent: Intent) {
        val reminderId = intent.getStringExtra("REMINDER_ID")
        Timber.i("received JobServiceIntent: $reminderId")
        reminderId?.let { sendNotification(it) }
    }

    //TODO: get the request id of the current geofence
    private fun sendNotification(reminderId: String) {
        CoroutineScope(coroutineContext).launch(SupervisorJob()) {

            val result = remindersLocalRepository.getReminder(reminderId)

            if (result is Result.Success<Reminder>) {
                val reminderDTO = result.data

                //send a notification to the user with the reminder details
                sendNotifications(
                    this@GeofenceTransitionsJobIntentService, Reminder(
                        reminderDTO.title,
                        reminderDTO.description,
                        reminderDTO.location,
                        reminderDTO.latitude,
                        reminderDTO.longitude,
                        reminderDTO.id
                    )
                )
            }
        }
    }
}